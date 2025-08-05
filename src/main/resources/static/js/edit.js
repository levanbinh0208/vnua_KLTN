function showEditForm(button) {
    // 1. Ẩn tất cả các form đang mở
    const allForms = document.querySelectorAll(".user-card form");
    allForms.forEach(f => f.style.display = "none");

    // 2. Tìm form nằm trong user-card chứa nút "Sửa" vừa nhấn
    const userCard = button.closest(".user-card");
    const editForm = userCard.querySelector("form");

    // 3. Hiển thị form đó
    if (editForm) {
        editForm.style.display = "block";
    }
}

function cancelEdit(button) {
    const form = button.closest("form");
    if (form) {
        form.style.display = "none";
    }
}