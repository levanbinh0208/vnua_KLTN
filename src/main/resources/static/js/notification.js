// notification.js - Hiển thị thông báo cho giảng viên

async function loadUnreadCount() {
    try {
        const response = await fetch('/notification/unread/count');
        const data = await response.json();

        const badge = document.getElementById('notification-badge');
        if (badge && data.count > 0) {
            badge.textContent = data.count;
            badge.style.display = 'inline-block';
        } else if (badge) {
            badge.style.display = 'none';
        }
    } catch (error) {
        console.error('Lỗi khi tải số thông báo:', error);
    }
}

async function loadNotifications() {
    try {
        const response = await fetch('/notification/unread');
        const notifications = await response.json();

        const container = document.getElementById('notification-list');
        if (!container) return;

        container.innerHTML = '';

        if (notifications.length === 0) {
            container.innerHTML = '<div class="notification-empty">Không có thông báo mới</div>';
            return;
        }

        notifications.forEach(notification => {
            const item = createNotificationItem(notification);
            container.appendChild(item);
        });
    } catch (error) {
        console.error('Lỗi khi tải thông báo:', error);
    }
}

function createNotificationItem(notification) {
    const div = document.createElement('div');
    div.className = 'notification-item' + (notification.status === 0 ? ' unread' : '');
    div.dataset.id = notification.notificationId;

    let icon = '📄';
    if (notification.type === 'book') icon = '📚';
    else if (notification.type === 'publication') icon = '📰';
    else if (notification.type === 'project') icon = '🔬';
    else if (notification.type === 'conference') icon = '🎤';
    else if (notification.type === 'patent') icon = '🏆';
    else if (notification.type === 'supervision') icon = '👨‍🎓';

    const isApproved = notification.message.includes('đã được phê duyệt');
    const statusClass = isApproved ? 'approved' : 'rejected';

    div.innerHTML = `
    <div class="notification-icon ${statusClass}">${icon}</div>
    <div class="notification-content">
      <div class="notification-title">${notification.title}</div>
      <div class="notification-message">${notification.message}</div>
      <div class="notification-time">${formatTime(notification.createdAt)}</div>
    </div>
    <button class="notification-delete" onclick="deleteNotification(${notification.notificationId})">
      <i class="fas fa-times"></i>
    </button>
  `;

    div.addEventListener('click', (e) => {
        if (!e.target.classList.contains('notification-delete')) {
            markAsRead(notification.notificationId);
        }
    });

    return div;
}

async function markAsRead(notificationId) {
    try {
        await fetch(`/notification/${notificationId}/read`, { method: 'PUT' });
        loadUnreadCount();
        loadNotifications();
    } catch (error) {
        console.error('Lỗi khi đánh dấu đã đọc:', error);
    }
}

async function markAllAsRead() {
    try {
        await fetch('/notification/read-all', { method: 'PUT' });
        loadUnreadCount();
        loadNotifications();
        alert('Đã đánh dấu tất cả thông báo đã đọc');
    } catch (error) {
        console.error('Lỗi:', error);
    }
}

async function deleteNotification(notificationId) {
    if (!confirm('Bạn có chắc muốn xóa thông báo này?')) return;

    try {
        await fetch(`/notification/${notificationId}`, { method: 'DELETE' });
        loadUnreadCount();
        loadNotifications();
    } catch (error) {
        console.error('Lỗi khi xóa:', error);
    }
}

function formatTime(dateString) {
    const date = new Date(dateString);
    const now = new Date();
    const diff = Math.floor((now - date) / 1000);

    if (diff < 60) return 'Vừa xong';
    if (diff < 3600) return Math.floor(diff / 60) + ' phút trước';
    if (diff < 86400) return Math.floor(diff / 3600) + ' giờ trước';
    if (diff < 604800) return Math.floor(diff / 86400) + ' ngày trước';

    return date.toLocaleDateString('vi-VN');
}

function toggleNotificationDropdown() {
    const dropdown = document.getElementById('notification-dropdown');
    if (dropdown) {
        dropdown.classList.toggle('show');

        if (dropdown.classList.contains('show')) {
            loadNotifications();
        }
    }
}

document.addEventListener('click', (e) => {
    const dropdown = document.getElementById('notification-dropdown');
    const button = document.getElementById('notification-button');

    if (dropdown && button &&
        !dropdown.contains(e.target) &&
        !button.contains(e.target)) {
        dropdown.classList.remove('show');
    }
});

document.addEventListener('DOMContentLoaded', () => {
    loadUnreadCount();
    setInterval(loadUnreadCount, 30000); // Auto refresh mỗi 30 giây
});

window.markAllAsRead = markAllAsRead;
window.deleteNotification = deleteNotification;
window.toggleNotificationDropdown = toggleNotificationDropdown;