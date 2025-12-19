let selectedUserId = null;

function showDeleteConfirm(element) {
    selectedUserId = element.getAttribute('data-id');
    console.log("ID người dùng muốn xóa:", selectedUserId);
    document.getElementById("deleteConfirmBox").style.display = "block";
}

function confirmDeleteYes() {
    if (!selectedUserId) {
        alert("Không tìm thấy ID người dùng để xóa.");
        return;
    }
    window.location.href = "/index/profile/del/" + selectedUserId;
}

function confirmDeleteNo() {
    selectedUserId = null;
    document.getElementById("deleteConfirmBox").style.display = "none";
}
