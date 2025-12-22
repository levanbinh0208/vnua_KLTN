const qs = (s, el = document) => el.querySelector(s);
const qsa = (s, el = document) => [...el.querySelectorAll(s)];

async function authFetch(url, options) {
    if (!options) options = {};

    options.credentials = 'include';

    return fetch(url, options);
}

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

qs('#search')?.addEventListener('input', e => {
    const kw = e.target.value.toLowerCase().trim();
    qsa('.tab-panel:not(.hidden) tbody tr').forEach(tr => {
        tr.style.display = tr.textContent.toLowerCase().includes(kw) ? '' : 'none';
    });
});

const modal = qs('#modal');
const modalFields = qs('#modal-fields');
const modalTitle = qs('#modal-title');

function openModal(tab, data = {}) {
    if (!modal || !modalFields || !modalTitle) return;

    modal.classList.remove('hidden');
    modalFields.innerHTML = buildFields(tab, data);
    modalTitle.textContent = (data.id || data.pubId ? "Sửa " : "Thêm mới ") + labelByTab(tab);

    const fileInput = qs('#modal-form input[name="wordFile"]');
    const fileLabel = qs('#file-name');
    if (fileInput && fileLabel) {
        if (data.wordFileName) {
            fileLabel.textContent = `Đã lưu: ${data.wordFileName}`;
        } else {
            fileLabel.textContent = '';
        }

        fileInput.addEventListener('change', () => {
            const file = fileInput.files[0];
            fileLabel.textContent = file ? `Đã chọn: ${file.name}` : '';
        });
    }

    if (tab === 'publication') {
        loadAuthorsList(data.authors);
    }
}

function closeModal() {
    modal?.classList.add('hidden');
}

qs('#btn-add')?.addEventListener('click', () => {
    const active = qs('.tab-btn.active')?.dataset.tab || 'publication';
    openModal(active);
});

if (modal) {
    modal.addEventListener('click', e => {
        if (e.target.closest('[data-close]') || e.target === modal) {
            closeModal();
        }
    });
}

qs('#modal-form')?.addEventListener('submit', async e => {
    e.preventDefault();
    try {
        const active = qs('.tab-btn.active')?.dataset.tab;
        if (!active) throw new Error('Không xác định tab đang hoạt động.');

        const formData = new FormData(e.target);
        const rowIndex = formData.get('rowIndex') ? Number(formData.get('rowIndex')) - 1 : -1;
        const row = rowIndex >= 0 ? qsa('#tbody-' + active + ' tr')[rowIndex] : null;

        const id = row?.dataset.id;

        let url = active;
        let method = 'POST';
        if (id) {
            url += '/' + id;
            method = 'PUT';
        }

        const res = await authFetch(url, {
            method,
            body: formData,
        });

        if (!res.ok) {
            const errText = await res.text();
            throw new Error(`HTTP ${res.status}: ${errText}`);
        }

        await loadData(active);
        closeModal();
        alert(`${id ? 'Cập nhật' : 'Thêm'} ${labelByTab(active)} thành công!`);

    } catch (err) {
        console.error('Lỗi khi lưu dữ liệu:', err);
        alert('Lỗi khi lưu dữ liệu: ' + (err.message || err.toString()));
    }
});

function labelByTab(tab) {
    return {
        publication: "bài báo",
        project: "đề tài",
        conference: "hội thảo",
        book: "sách",
        patent: "bằng sáng chế",
        supervision: "hướng dẫn"
    }[tab] || tab;
}

function buildFields(tab, data = {}) {
    const hiddenFields = `
        <input type="hidden" name="rowIndex" value="${data.rowIndex || ''}">
        <input type="hidden" name="wordFileName" value="${data.wordFileName || ''}">
    `;

    const fileField = `
        <div>
            <label>Tải file</label>
            <input type="file" name="wordFile" accept=".doc,.docx">
            <small id="file-name"></small>
        </div>
    `;

    switch (tab) {
        case 'publication':
            return hiddenFields + `
                <div><label>Tiêu đề</label><input name="title" value="${data.title || ''}" required></div>
                <div><label>Tác giả</label>
                    <select id="authors-select" name="authors" multiple style="width:100%;padding:5px;"></select>
                </div>
                <div><label>Tạp chí</label><input name="journal" value="${data.journal || ''}"></div>
                <div><label>Năm</label><input type="number" name="year" value="${data.year || ''}"></div>
                ${fileField}
            `;
        case 'project':
            return hiddenFields + `
                <div><label>Tên đề tài</label><input name="name" value="${data.name || ''}" required></div>
                <div><label>Vai trò</label><input name="role" value="${data.role || ''}"></div>
                <div><label>Bắt đầu</label><input type="date" name="startDate" value="${data.startDate || ''}"></div>
                <div><label>Kết thúc</label><input type="date" name="endDate" value="${data.endDate || ''}"></div>
                ${fileField}
            `;
        case 'conference':
            return hiddenFields + `
                <div><label>Tiêu đề</label><input name="title" value="${data.title || ''}" required></div>
                <div><label>Địa điểm</label><input name="location" value="${data.location || ''}"></div>
                <div><label>Ngày</label><input type="date" name="date" value="${data.date || ''}"></div>
                <div><label>Vai trò</label><input name="role" value="${data.role || ''}"></div>
                ${fileField}
            `;
        case 'book':
            return hiddenFields + `
                <div><label>Tựa sách</label><input name="title" value="${data.title || ''}" required></div>
                <div><label>NXB</label><input name="publisher" value="${data.publisher || ''}"></div>
                <div><label>Năm</label><input type="number" name="year" value="${data.year || ''}"></div>
                <div><label>ISBN</label><input name="isbn" value="${data.isbn || ''}"></div>
                ${fileField}
            `;
        case 'patent':
            return hiddenFields + `
                <div><label>Tiêu đề</label><input name="title" value="${data.title || ''}" required></div>
                <div><label>Số bằng</label><input name="patentNo" value="${data.patentNo || ''}"></div>
                <div><label>Năm</label><input type="number" name="year" value="${data.year || ''}"></div>
                <div><label>Trạng thái</label><input name="status" value="${data.status || ''}"></div>
                ${fileField}
            `;
        case 'supervision':
            return hiddenFields + `
                <div><label>Sinh viên</label><input name="studentName" value="${data.studentName || ''}" required></div>
                <div><label>Bậc</label><input name="level" value="${data.level || ''}"></div>
                <div><label>Đề tài</label><input name="thesisTitle" value="${data.thesisTitle || ''}"></div>
                <div><label>Năm</label><input type="number" name="year" value="${data.year || ''}"></div>
                ${fileField}
            `;
        default:
            return hiddenFields;
    }
}

async function loadAuthorsList(selected = "") {
    try {
        const res = await authFetch("/authors");
        if (!res.ok) throw new Error("Không thể tải danh sách tác giả!");

        const raw = await res.json();

        const authors = raw
            .flatMap(str => str.split(','))
            .map(name => name.trim())
            .filter(name => name.length > 0);

        const uniqueAuthors = [...new Set(authors)];

        const select = qs('#authors-select');
        if (!select) return;

        const selectedList = Array.isArray(selected)
            ? selected
            : (selected || "").split(",").map(s => s.trim());

        select.innerHTML = uniqueAuthors.map(author =>
            `<option value="${author}" ${selectedList.includes(author) ? "selected" : ""}>${author}</option>`
        ).join('');
    } catch (err) {
        console.error("Lỗi tải tác giả:", err);
        alert("Không thể tải danh sách tác giả.");
    }
}

async function loadData(tab) {
    try {
        const res = await authFetch(tab);
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

    const id = d.pubId || d.projectId || d.confId || d.bookId || d.patentId || d.supId || '';

    const html = `
        <tr data-id="${id}">
            <td>${idx}</td>
            ${map[tab].map(c => `<td>${d[c] || ''}</td>`).join('')}
            <td>
                <button data-action="edit" data-row="${idx}">Sửa</button>
                <button data-action="delete" data-row="${idx}">Xóa</button>
                ${d.wordFileName ? `<button data-action="download" data-id="${id}">Xuất</button>` : ''}
            </td>
        </tr>`;

    tb.insertAdjacentHTML('beforeend', html);
    const row = tb.lastElementChild;

    row.querySelector('[data-action="edit"]')?.addEventListener('click', () => handleEdit(tab, row, idx));
    row.querySelector('[data-action="delete"]')?.addEventListener('click', () => handleDelete(tab, row));
    row.querySelector('[data-action="download"]')?.addEventListener('click', () => {
        const id = row.dataset.id;
        if (id) {
            window.open(`/${tab}/${id}/file`, '_blank');
        }
    });
}

function handleEdit(tab, row, rowIndex) {
    const id = row.dataset.id;
    if (!id) return alert("Không tìm thấy ID bản ghi!");

    authFetch(`/${tab}/${id}`)
        .then(res => {
            if (!res.ok) throw new Error(`HTTP ${res.status}`);
            return res.json();
        })
        .then(data => {
            data.rowIndex = rowIndex;
            openModal(tab, data);
        })
        .catch(err => {
            console.error("Lỗi khi tải dữ liệu để sửa:", err);
            alert("Không thể tải dữ liệu để sửa.");
        });
}

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

async function saveData(tab, data, method = 'POST', asyncReturn = false) {
    const idField = {
        patent: 'patentId',
        publication: 'pubId',
        conference: 'confId',
        supervision: 'supId',
        book : 'bookId',
    }[tab] || 'id';
    const id = data[idField] || data.id || '';
    const url = method === 'POST' ? `/${tab}` : `/${tab}/${id}`;

    const res = await authFetch(url, {
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

async function deleteData(tab, id) {
    const res = await authFetch(`/${tab}/${id}`, { method: 'DELETE' });
    if (!res.ok) throw new Error('Lỗi khi xóa dữ liệu!');
    return true;
}

function updateRowIndices(tab) {
    qsa('#tbody-' + tab + ' tr').forEach((row, i) => {
        const firstTd = row.querySelector('td:first-child');
        if (firstTd) firstTd.textContent = i + 1;
        row.querySelectorAll('button[data-row]').forEach(btn => {
            btn.dataset.row = i + 1;
        });
    });
}

document.addEventListener("DOMContentLoaded", () => {
    qs('#modal')?.addEventListener('change', e => {
        if (e.target.name === 'wordFile') {
            const fileName = e.target.files[0]?.name || '';
            qs('#file-name').textContent = fileName ? `Đã chọn: ${fileName}` : '';
        }
    });
    const firstTab = qs(".tab-btn");
    if (firstTab) firstTab.click();
});