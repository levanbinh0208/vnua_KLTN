// ===================== 🔧 TRỢ GIÚP CƠ BẢN =====================
const qs = (s, el = document) => el.querySelector(s);
const qsa = (s, el = document) => [...el.querySelectorAll(s)];

// ===================== 🧭 QUẢN LÝ TAB =====================
qsa('.tab-btn').forEach(btn => {
    btn.addEventListener('click', async () => {
        qsa('.tab-btn').forEach(b => b.classList.remove('active'));
        btn.classList.add('active');

        const tab = btn.dataset.tab;
        qsa('.tab-panel').forEach(p => p.classList.add('hidden'));
        const panel = qs('#tab-' + tab);
        if (panel) panel.classList.remove('hidden');

        await loadData(tab);
    });
});

// ===================== 🔍 TÌM KIẾM =====================
qs('#search')?.addEventListener('input', e => {
    const kw = e.target.value.toLowerCase().trim();
    qsa('.tab-panel:not(.hidden) tbody tr').forEach(tr => {
        tr.style.display = tr.textContent.toLowerCase().includes(kw) ? '' : 'none';
    });
});

// ===================== 🪟 QUẢN LÝ MODAL =====================
const modal = qs('#modal');
const modalFields = qs('#modal-fields');
const modalTitle = qs('#modal-title');

function openModal(tab, data = {}) {
    if (!modal || !modalFields || !modalTitle) return;

    modal.classList.remove('hidden');
    modalFields.innerHTML = buildFields(tab, data);
    modalTitle.textContent = (data.id || data.pubId ? "Sửa " : "Thêm mới ") + labelByTab(tab);

    // Nếu là tab "publication" thì tải danh sách tác giả
    if (tab === 'publication') loadAuthorsList(data.authors);
}

function closeModal() {
    modal?.classList.add('hidden');
}

// Nút + thêm mới
qs('#btn-add')?.addEventListener('click', () => {
    const active = qs('.tab-btn.active')?.dataset.tab || 'publication';
    openModal(active);
});

// Đóng modal
if (modal) {
    modal.addEventListener('click', e => {
        if (e.target.closest('[data-close]') || e.target === modal) closeModal();
    });
}

// ===================== 💾 XỬ LÝ SUBMIT FORM =====================
qs('#modal-form')?.addEventListener('submit', async e => {
    e.preventDefault();
    try {
        const active = qs('.tab-btn.active')?.dataset.tab;
        if (!active) throw new Error('Không xác định tab đang hoạt động.');

        const formData = new FormData(e.target);
        const data = Object.fromEntries(formData.entries());
        const rowIndex = data.rowIndex ? Number(data.rowIndex) - 1 : -1;
        const row = rowIndex >= 0 ? qsa('#tbody-' + active + ' tr')[rowIndex] : null;

        if (row) {
            const id = row.dataset.id;
            await updateRow(active, row, { ...data, id });
        } else {
            await saveData(active, data, 'POST', false);
        }
        closeModal();
    } catch (err) {
        console.error('Lỗi khi lưu dữ liệu:', err);
        alert('Lỗi khi lưu dữ liệu: ' + (err.message || err));
    }
});

// ===================== 🏷️ TÊN TAB HIỂN THỊ =====================
function labelByTab(tab) {
    return {
        publication: "bài báo",
        project: "đề tài",
        conference: "hội thảo",
        book: "sách",
        patent: "bằng sáng chế",
        supervision: "hướng dẫn"
    }[tab];
}

// ===================== 🧱 XÂY FORM CHO MODAL =====================
function buildFields(tab, data = {}) {
    switch (tab) {
        case 'publication':
            return `
                <input type="hidden" name="rowIndex" value="${data.rowIndex || ''}">
                <div><label>Tiêu đề</label><input name="title" value="${data.title || ''}" required></div>
                <div><label>Tác giả</label>
                    <select id="authors-select" name="authors" multiple style="width:100%;padding:5px;"></select>
                </div>
                <div><label>Tạp chí</label><input name="journal" value="${data.journal || ''}"></div>
                <div><label>Năm</label><input type="number" name="year" value="${data.year || ''}"></div>`;
        case 'project':
            return `
                <input type="hidden" name="rowIndex" value="${data.rowIndex || ''}">
                <div><label>Tên đề tài</label><input name="name" value="${data.name || ''}" required></div>
                <div><label>Vai trò</label><input name="role" value="${data.role || ''}"></div>
                <div><label>Bắt đầu</label><input type="date" name="startDate" value="${data.startDate || ''}"></div>
                <div><label>Kết thúc</label><input type="date" name="endDate" value="${data.endDate || ''}"></div>`;
        case 'conference':
            return `
                <input type="hidden" name="rowIndex" value="${data.rowIndex || ''}">
                <div><label>Tiêu đề</label><input name="title" value="${data.title || ''}" required></div>
                <div><label>Địa điểm</label><input name="location" value="${data.location || ''}"></div>
                <div><label>Ngày</label><input type="date" name="date" value="${data.date || ''}"></div>
                <div><label>Vai trò</label><input name="role" value="${data.role || ''}"></div>`;
        case 'book':
            return `
                <input type="hidden" name="rowIndex" value="${data.rowIndex || ''}">
                <div><label>Tựa sách</label><input name="title" value="${data.title || ''}" required></div>
                <div><label>NXB</label><input name="publisher" value="${data.publisher || ''}"></div>
                <div><label>Năm</label><input type="number" name="year" value="${data.year || ''}"></div>
                <div><label>ISBN</label><input name="isbn" value="${data.isbn || ''}"></div>`;
        case 'patent':
            return `
                <input type="hidden" name="rowIndex" value="${data.rowIndex || ''}">
                <div><label>Tiêu đề</label><input name="title" value="${data.title || ''}" required></div>
                <div><label>Số bằng</label><input name="patentNo" value="${data.patentNo || ''}"></div>
                <div><label>Năm</label><input type="number" name="year" value="${data.year || ''}"></div>
                <div><label>Trạng thái</label><input name="status" value="${data.status || ''}"></div>`;
        case 'supervision':
            return `
                <input type="hidden" name="rowIndex" value="${data.rowIndex || ''}">
                <div><label>Sinh viên</label><input name="studentName" value="${data.studentName || ''}" required></div>
                <div><label>Bậc</label><input name="level" value="${data.level || ''}"></div>
                <div><label>Đề tài</label><input name="thesisTitle" value="${data.thesisTitle || ''}"></div>
                <div><label>Năm</label><input type="number" name="year" value="${data.year || ''}"></div>`;
    }
}

// ===================== 👥 TẢI DANH SÁCH TÁC GIẢ =====================
async function loadAuthorsList(selected = "") {
    try {
        const res = await fetch("/api/authors");
        if (!res.ok) throw new Error("Không thể tải danh sách tác giả!");
        const authors = await res.json();

        const select = qs('#authors-select');
        if (!select) return;

        select.innerHTML = authors.map(a =>
            `<option value="${name}" ${selected.includes(name) ? 'selected' : ''}>${name}</option>`
        ).join('');
    } catch (err) {
        console.error("Lỗi tải tác giả:", err);
    }
}

// ===================== 📦 LOAD DỮ LIỆU =====================
async function loadData(tab) {
    try {
        const res = await fetch("/api/" + tab);
        if (!res.ok) throw new Error('Không tải được dữ liệu từ server');
        const data = await res.json();
        const tb = qs("#tbody-" + tab);
        if (!tb) return;
        tb.innerHTML = "";
        data.forEach((d, i) => appendRow(tab, d, i + 1));
    } catch (err) {
        console.error("Lỗi tải dữ liệu:", err);
        alert('Lỗi tải dữ liệu: ' + (err.message || err));
    }
}

// ===================== 🧩 THÊM DÒNG VÀO BẢNG =====================
function appendRow(tab, d, idx) {
    const tb = qs('#tbody-' + tab);
    if (!tb) return;

    const map = {
        publication: ['title', 'authors', 'journal', 'year'],
        project: ['name', 'role', 'startDate', 'endDate'],
        conference: ['title', 'location', 'date', 'role'],
        book: ['title', 'publisher', 'year', 'isbn'],
        patent: ['title', 'patentNo', 'year', 'status'],
        supervision: ['studentName', 'level', 'thesisTitle', 'year']
    };

    const html = `
        <tr data-id="${d.pubId || d.projectId || d.confId || d.id || d.patentId || d.supId || ''}">
            <td>${idx}</td>
            ${map[tab].map(c => `<td>${d[c] || ''}</td>`).join('')}
            <td>
                <button data-action="edit" data-row="${idx}">✏️</button>
                <button data-action="delete" data-row="${idx}">🗑</button>
            </td>
        </tr>`;

    tb.insertAdjacentHTML('beforeend', html);
    const row = tb.lastElementChild;
    row.querySelector('[data-action="edit"]').addEventListener('click', () => handleEdit(tab, row, idx));
    row.querySelector('[data-action="delete"]').addEventListener('click', () => handleDelete(tab, row));
}

// ===================== ✏️ SỬA DỮ LIỆU =====================
function handleEdit(tab, row, rowIndex) {
    const id = row.dataset.id;
    if (!id) return alert("Không tìm thấy ID bản ghi!");

    fetch(`/api/${tab}/${id}`)
        .then(res => res.json())
        .then(data => {
            data.rowIndex = rowIndex;
            openModal(tab, data);
        })
        .catch(err => {
            console.error("Lỗi khi tải dữ liệu để sửa:", err);
            alert("Không thể tải dữ liệu.");
        });
}

// ===================== 🗑 XÓA DỮ LIỆU =====================
async function handleDelete(tab, row) {
    const id = row.dataset.id;
    if (!id || !confirm('Bạn có chắc muốn xóa?')) return;
    try {
        await deleteData(tab, id);
        row.remove();
        updateRowIndices(tab);
    } catch (err) {
        console.error('Lỗi xóa:', err);
        alert('Không thể xóa: ' + (err.message || err));
    }
}

// ===================== 🔁 CẬP NHẬT HÀNG =====================
async function updateRow(tab, row, data) {
    const fieldsByTab = {
        publication: ['title', 'authors', 'journal', 'year'],
        project: ['name', 'role', 'startDate', 'endDate'],
        conference: ['title', 'location', 'date', 'role'],
        book: ['title', 'publisher', 'year', 'isbn'],
        patent: ['title', 'patentNo', 'year', 'status'],
        supervision: ['studentName', 'level', 'thesisTitle', 'year']
    };

    const updatedData = await saveData(tab, data, 'PUT', true);
    const cells = row.querySelectorAll('td:not(:first-child):not(:last-child)');

    fieldsByTab[tab].forEach((field, i) => {
        if (cells[i]) cells[i].textContent = updatedData[field] || '';
    });
    alert(`Cập nhật ${labelByTab(tab)} thành công!`);
}

// ===================== 💾 LƯU DỮ LIỆU =====================
async function saveData(tab, data, method = 'POST', asyncReturn = false) {
    const idField = tab === 'patent' ? 'patentId'
        : tab === 'publication' ? 'pubId'
            : tab === 'conference' ? 'confId'
                : tab === 'supervision' ? 'supId'
                    : 'id';
    const id = data[idField] || data.id || '';
    const url = method === 'POST' ? `/api/${tab}` : `/api/${tab}/${id}`;

    const res = await fetch(url, {
        method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    });
    if (!res.ok) throw new Error('Lỗi khi lưu dữ liệu!');
    const json = await res.json();

    if (!asyncReturn) {
        await loadData(tab);
        alert(`${method === 'POST' ? 'Thêm' : 'Cập nhật'} ${labelByTab(tab)} thành công!`);
    }
    return json;
}

// ===================== ❌ XÓA DỮ LIỆU =====================
async function deleteData(tab, id) {
    const res = await fetch(`/api/${tab}/${id}`, { method: 'DELETE' });
    if (!res.ok) throw new Error('Lỗi khi xóa dữ liệu!');
    await loadData(tab);
    return true;
}

// ===================== 🔢 CẬP NHẬT STT =====================
function updateRowIndices(tab) {
    qsa('#tbody-' + tab + ' tr').forEach((row, i) => {
        const firstTd = row.querySelector('td:first-child');
        if (firstTd) firstTd.textContent = i + 1;
        row.querySelectorAll('button').forEach(btn => btn.dataset.row = i + 1);
    });
}

// ===================== 🚀 KHỞI ĐỘNG =====================
document.addEventListener("DOMContentLoaded", () => {
    const firstTab = qs(".tab-btn");
    if (firstTab) firstTab.click();
});
