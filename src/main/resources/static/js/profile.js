function showEditForm(button) {
    const card = button.closest('.user-card');
    const form = card.querySelector('form');
    const info = card.querySelector('.info');

    if (form && info) {
        form.style.display = 'block';
        info.style.display = 'none';
    }
}

function cancelEdit(button) {
    const form = button.closest('form');
    const card = button.closest('.user-card');
    const info = card.querySelector('.info');

    if (form && info) {
        form.style.display = 'none';
        info.style.display = 'block';
    }
    form.style.display = 'none';
    card.classList.remove('editing');
}
