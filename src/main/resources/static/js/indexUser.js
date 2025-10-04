// --- Helpers ---
const qs  = (s, el=document) => el.querySelector(s);
const qsa = (s, el=document) => [...el.querySelectorAll(s)];

// --- Tabs ---
qsa('.tab-btn').forEach(btn => {
    btn.addEventListener('click', () => {
        // Active button
        qsa('.tab-btn').forEach(b => b.classList.remove('active'));
        btn.classList.add('active');

        const tab = btn.dataset.tab;

        // Hide panels
        qsa('.tab-panel').forEach(p => p.classList.add('hidden'));
        qs('#tab-' + tab).classList.remove('hidden');

        // Load API cho tab
        loadData(tab);
    });
});

// --- Search filter (chỉ publications demo) ---
qs('#search')?.addEventListener('input', e => {
    const kw = e.target.value.toLowerCase().trim();
    qsa('#tbody-publication tr').forEach(tr => {
        tr.style.display = tr.textContent.toLowerCase().includes(kw) ? '' : 'none';
    });
});

// --- Modal ---
const modal = qs('#modal');
const modalFields = qs('#modal-fields');
const modalTitle = qs('#modal-title');

function openModal(tab) {
    modal.classList.remove('hidden');
    modalFields.innerHTML = buildFields(tab);
    modalTitle.textContent = "Thêm mới " + labelByTab(tab);
}
function closeModal() { modal.classList.add('hidden'); }

qs('#btn-add')?.addEventListener('click', () => {
    const active = qs('.tab-btn.active')?.dataset.tab || 'publication';
    openModal(active);
});
modal.addEventListener('click', e => {
    if (e.target.dataset.close || e.target === modal) closeModal();
});
qs('#modal-form')?.addEventListener('submit', e => {
    e.preventDefault();
    const active = qs('.tab-btn.active').dataset.tab;
    const data = Object.fromEntries(new FormData(e.target).entries());
    appendRow(active, data);
    closeModal();
});

// --- Label theo tab ---
function labelByTab(tab) {
    return {
        publication:"bài báo", project:"đề tài", conference:"hội thảo",
        book:"sách", patent:"bằng sáng chế", supervision:"hướng dẫn"
    }[tab];
}

// --- Form fields theo tab ---
function buildFields(tab) {
    switch(tab) {
        case 'publication': return `
          <div><label>Tiêu đề</label><input name="title" required></div>
          <div><label>Tác giả</label><input name="authors"></div>
          <div><label>Tạp chí</label><input name="journal"></div>
          <div><label>Năm</label><input type="number" name="year"></div>`;
        case 'project': return `
          <div><label>Tên đề tài</label><input name="name" required></div>
          <div><label>Vai trò</label><input name="role"></div>
          <div><label>Bắt đầu</label><input type="date" name="start"></div>
          <div><label>Kết thúc</label><input type="date" name="end"></div>`;
        case 'conference': return `
          <div><label>Tiêu đề</label><input name="title" required></div>
          <div><label>Địa điểm</label><input name="location"></div>
          <div><label>Ngày</label><input type="date" name="date"></div>
          <div><label>Vai trò</label><input name="role"></div>`;
        case 'book': return `
          <div><label>Tựa sách</label><input name="title" required></div>
          <div><label>NXB</label><input name="publisher"></div>
          <div><label>Năm</label><input type="number" name="year"></div>
          <div><label>ISBN</label><input name="isbn"></div>`;
        case 'patent': return `
          <div><label>Tiêu đề</label><input name="title" required></div>
          <div><label>Số bằng</label><input name="no"></div>
          <div><label>Năm</label><input type="number" name="year"></div>
          <div><label>Trạng thái</label><input name="status"></div>`;
        case 'supervision': return `
          <div><label>Sinh viên</label><input name="student" required></div>
          <div><label>Bậc</label><input name="level"></div>
          <div><label>Đề tài</label><input name="thesis"></div>
          <div><label>Năm</label><input type="number" name="year"></div>`;
    }
}

// --- Load API cho tab ---
function loadData(tab) {
    fetch("/api/" + tab)
        .then(res => res.json())
        .then(data => {
            const tb = qs("#tbody-" + tab);
            tb.innerHTML = "";
            data.forEach((d, i) => {
                appendRow(tab, d, i+1);
            });
        })
        .catch(err => console.error("Lỗi load dữ liệu:", err));
}

// --- Append row ---
function appendRow(tab, d, idx) {
    const tb = qs('#tbody-'+tab);
    const rowIndex = idx || tb.children.length + 1;
    let html = "";
    if (tab==='publication') {
        html = `<tr><td>${rowIndex}</td><td>${d.title||''}</td><td>${d.authors||''}</td><td>${d.journal||''}</td><td>${d.year||''}</td>
          <td><button>✏️</button> <button>🗑</button></td></tr>`;
    }
    if (tab==='project') {
        html = `<tr><td>${rowIndex}</td><td>${d.name||''}</td><td>${d.role||''}</td><td>${d.start||''}</td><td>${d.end||''}</td>
          <td><button>✏️</button> <button>🗑</button></td></tr>`;
    }
    if (tab==='conference') {
        html = `<tr><td>${rowIndex}</td><td>${d.title||''}</td><td>${d.location||''}</td><td>${d.date||''}</td><td>${d.role||''}</td>
          <td><button>✏️</button> <button>🗑</button></td></tr>`;
    }
    if (tab==='book') {
        html = `<tr><td>${rowIndex}</td><td>${d.title||''}</td><td>${d.publisher||''}</td><td>${d.year||''}</td><td>${d.isbn||''}</td>
          <td><button>✏️</button> <button>🗑</button></td></tr>`;
    }
    if (tab==='patent') {
        html = `<tr><td>${rowIndex}</td><td>${d.title||''}</td><td>${d.no||''}</td><td>${d.year||''}</td><td>${d.status||''}</td>
          <td><button>✏️</button> <button>🗑</button></td></tr>`;
    }
    if (tab==='supervision') {
        html = `<tr><td>${rowIndex}</td><td>${d.student||''}</td><td>${d.level||''}</td><td>${d.thesis||''}</td><td>${d.year||''}</td>
          <td><button>✏️</button> <button>🗑</button></td></tr>`;
    }
    tb.insertAdjacentHTML('beforeend', html);
}

// --- Auto load mặc định tab đầu tiên ---
document.addEventListener("DOMContentLoaded", () => {
    const firstTab = qs(".tab-btn");
    if (firstTab) {
        firstTab.click();
    }
});
