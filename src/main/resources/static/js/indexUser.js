const qs  = (s, el=document) => el.querySelector(s);
const qsa = (s, el=document) => Array.from(el.querySelectorAll(s));
qsa('.tab-btn').forEach(btn => {
    btn.addEventListener('click', () => {
        qsa('.tab-btn').forEach(b => b.classList.remove('active','bg-slate-900','text-white'));
        btn.classList.add('active','bg-slate-900','text-white');
        const tab = btn.dataset.tab;
        qsa('.tab-panel').forEach(p => p.classList.add('hidden'));
        qs('#tab-' + tab).classList.remove('hidden');
    });
});

    const search = qs('#search');
    search?.addEventListener('input', () => {
    const kw = search.value.toLowerCase().trim();
    qsa('#tbody-publication tr').forEach(tr => {
    const text = tr.textContent.toLowerCase();
    tr.style.display = text.includes(kw) ? '' : 'none';
});
});

    // --- Modal handling ---
    const modal = qs('#modal');
    const modalFields = qs('#modal-fields');
    const modalTitle = qs('#modal-title');

    function openModal(forTab){
    modal.classList.remove('hidden');
    document.body.style.overflow = 'hidden';
    // Build fields by tab (client-side demo only)
    modalFields.innerHTML = buildFields(forTab);
    modalTitle.textContent = 'Thêm mới ' + labelByTab(forTab);
}
    function closeModal(){ modal.classList.add('hidden'); document.body.style.overflow='auto'; }

    modal.addEventListener('click', (e)=>{ if(e.target.dataset.close) closeModal(); });
    qs('#btn-add').addEventListener('click', ()=>{
    const active = qs('.tab-btn.active')?.dataset.tab || 'publication';
    openModal(active);
});
qs('#modal-form').addEventListener('submit', (e) => {
    e.preventDefault();
    const active = qs('.tab-btn.active')?.dataset.tab || 'publication';
    const data = Object.fromEntries(new FormData(e.target).entries());
    appendRow(active, data);
    closeModal();
    e.target.reset();
});
function labelByTab(tab) {
    return ({
        publication: 'bài báo',
        project: 'đề tài/dự án',
        conference: 'hội thảo',
        book: 'sách',
        patent: 'bằng sáng chế',
        supervision: 'hướng dẫn'
    })[tab] || '';
}
function buildFields(tab) {
    switch(tab) {
        case 'publication':
            return `
                <div><label class='text-sm'>Tiêu đề</label><input name='title' required class='mt-1 w-full border border-slate-200 rounded-xl px-3 py-2'></div>
                <div><label class='text-sm'>Tác giả</label><input name='authors' class='mt-1 w-full border border-slate-200 rounded-xl px-3 py-2'></div>
                <div><label class='text-sm'>Tạp chí</label><input name='journal' class='mt-1 w-full border border-slate-200 rounded-xl px-3 py-2'></div>
                <div><label class='text-sm'>Năm</label><input name='year' type='number' min='1950' max='2030' class='mt-1 w-full border border-slate-200 rounded-xl px-3 py-2'></div>
                <div class='sm:col-span-2'><label class='text-sm'>URL / PDF</label><input name='url' class='mt-1 w-full border border-slate-200 rounded-xl px-3 py-2' placeholder='https://...'></div>
                <div class='sm:col-span-2'><label class='text-sm'>Abstract</label><textarea name='abstract' rows='3' class='mt-1 w-full border border-slate-200 rounded-xl px-3 py-2'></textarea></div>`;
        case 'project':
            return `
                <div class='sm:col-span-2'><label class='text-sm'>Tên đề tài</label><input name='name' required class='mt-1 w-full border rounded-xl px-3 py-2 border-slate-200'></div>
                <div><label class='text-sm'>Vai trò</label><input name='role' class='mt-1 w-full border rounded-xl px-3 py-2 border-slate-200' placeholder='Chủ nhiệm / Thành viên'></div>
                <div><label class='text-sm'>Bắt đầu</label><input name='start_date' type='date' class='mt-1 w-full border rounded-xl px-3 py-2 border-slate-200'></div>
                <div><label class='text-sm'>Kết thúc</label><input name='end_date' type='date' class='mt-1 w-full border rounded-xl px-3 py-2 border-slate-200'></div>
                <div class='sm:col-span-2'><label class='text-sm'>Mô tả</label><textarea name='description' rows='3' class='mt-1 w-full border rounded-xl px-3 py-2 border-slate-200'></textarea></div>`;
        case 'conference':
            return `
                <div class='sm:col-span-2'><label class='text-sm'>Tiêu đề</label><input name='title' required class='mt-1 w-full border rounded-xl px-3 py-2 border-slate-200'></div>
                <div><label class='text-sm'>Địa điểm</label><input name='location' class='mt-1 w-full border rounded-xl px-3 py-2 border-slate-200'></div>
                <div><label class='text-sm'>Ngày</label><input name='date' type='date' class='mt-1 w-full border rounded-xl px-3 py-2 border-slate-200'></div>
                <div><label class='text-sm'>Vai trò</label><input name='role' class='mt-1 w-full border rounded-xl px-3 py-2 border-slate-200'></div>`;
        case 'book':
            return `
                <div class='sm:col-span-2'><label class='text-sm'>Tựa sách</label><input name='title' required class='mt-1 w-full border rounded-xl px-3 py-2 border-slate-200'></div>
                <div><label class='text-sm'>Nhà xuất bản</label><input name='publisher' class='mt-1 w-full border rounded-xl px-3 py-2 border-slate-200'></div>
                <div><label class='text-sm'>Năm</label><input name='year' type='number' min='1950' max='2030' class='mt-1 w-full border rounded-xl px-3 py-2 border-slate-200'></div>
                <div><label class='text-sm'>ISBN</label><input name='isbn' class='mt-1 w-full border rounded-xl px-3 py-2 border-slate-200'></div>`;
        case 'patent':
            return `
                <div class='sm:col-span-2'><label class='text-sm'>Tiêu đề</label><input name='title' required class='mt-1 w-full border rounded-xl px-3 py-2 border-slate-200'></div>
                <div><label class='text-sm'>Số bằng</label><input name='patent_no' class='mt-1 w-full border rounded-xl px-3 py-2 border-slate-200'></div>
                <div><label class='text-sm'>Năm</label><input name='year' type='number' min='1950' max='2030' class='mt-1 w-full border rounded-xl px-3 py-2 border-slate-200'></div>
                <div><label class='text-sm'>Trạng thái</label><input name='status' placeholder='Đã cấp / Đang xét duyệt' class='mt-1 w-full border rounded-xl px-3 py-2 border-slate-200'></div>`;
        case 'supervision':
            return `
                <div><label class='text-sm'>Sinh viên</label><input name='student_name' required class='mt-1 w-full border rounded-xl px-3 py-2 border-slate-200'></div>
                <div><label class='text-sm'>Bậc</label><input name='level' placeholder='Khóa luận / Thạc sĩ / Tiến sĩ' class='mt-1 w-full border rounded-xl px-3 py-2 border-slate-200'></div>
                <div class='sm:col-span-2'><label class='text-sm'>Đề tài</label><input name='thesis_title' class='mt-1 w-full border rounded-xl px-3 py-2 border-slate-200'></div>
                <div><label class='text-sm'>Năm</label><input name='year' type='number' min='2000' max='2030' class='mt-1 w-full border rounded-xl px-3 py-2 border-slate-200'></div>`;
    }
}
document.addEventListener("DOMContentLoaded", function () {
    document.querySelector('[data-tab="book"]').addEventListener("click", function (e) {
        e.preventDefault();

        fetch("/api/book")
            .then(res => res.json())
            .then(data => {
                let rows = "";
                data.forEach((b, idx) => {
                    rows += `
            <tr>
              <td class="px-4 py-3 text-center whitespace-nowrap">${idx + 1}</td>
              <td class="px-4 py-3 text-center whitespace-nowrap">${b.title}</td>
              <td class="px-4 py-3 text-center whitespace-nowrap">${b.publisher}</td>
              <td class="px-4 py-3 text-center whitespace-nowrap">${b.year}</td>
              <td class="px-4 py-3 text-center whitespace-nowrap">Tác giả</td>
              <td class="px-4 py-3 text-right whitespace-nowrap">
                <button class="text-blue-600">✏️ Sửa</button>
                <button class="text-red-600">🗑 Xóa</button>
              </td>
            </tr>
          `;
                });
                document.getElementById("tbody-book").innerHTML = rows;

                // Hiện tab book
                document.querySelectorAll(".tab-panel").forEach(tab => tab.classList.add("hidden"));
                document.getElementById("tab-book").classList.remove("hidden");
            });
    });
});
function appendRow(tab, d) {
    const map = {
        publication: ['title','authors','journal','year'],
        project: ['name','role',null,'start_date','end_date'],
        conference: ['title','location','date'],
        book: ['title','publisher','year'],
        patent: ['title','patent_no','year'],
        supervision: ['student_name','thesis_title','level','year']
    };
    const tb = qs('#tbody-' + tab);
    const idx = tb.children.length + 1;
    let html = '';
    switch(tab) {
        case 'publication':
            html = `<tr>
                      <td class='px-4 py-3'>${idx}</td>
                      <td class='px-4 py-3 font-medium'>${d.title || ''}</td>
                      <td class='px-4 py-3'>${d.authors || ''}</td>
                      <td class='px-4 py-3'>${d.journal || ''}</td>
                      <td class='px-4 py-3'>${d.year || ''}</td>
                      <td class='px-4 py-3 text-right'><a href='#' class='text-slate-600 hover:underline mr-3'>Sửa</a><a href='#' class='text-rose-600 hover:underline'>Xóa</a></td>
                    </tr>`;
            break;
        case 'project':
            html = `<tr>
                      <td class='px-4 py-3'>${idx}</td>
                      <td class='px-4 py-3 font-medium'>${d.name || ''}</td>
                      <td class='px-4 py-3'>${d.role || ''}</td>
                      <td class='px-4 py-3'>${(d.start_date||'')}${d.end_date?(' → ' + d.end_date):''}</td>
                      <td class='px-4 py-3 text-right'><a href='#' class='text-slate-600 hover:underline mr-3'>Sửa</a><a href='#' class='text-rose-600 hover:underline'>Xóa</a></td>
                    </tr>`;
            break;
        case 'conference':
            html = `<tr>
                      <td class='px-4 py-3'>${idx}</td>
                      <td class='px-4 py-3 font-medium'>${d.title || ''}</td>
                      <td class='px-4 py-3'>${d.location || ''}</td>
                      <td class='px-4 py-3'>${d.date || ''}</td>
                      <td class='px-4 py-3 text-right'><a href='#' class='text-slate-600 hover:underline mr-3'>Sửa</a><a href='#' class='text-rose-600 hover:underline'>Xóa</a></td>
                    </tr>`;
            break;
        case 'book':
            html = `<tr>
                      <td class='px-4 py-3'>${idx}</td>
                      <td class='px-4 py-3 font-medium'>${d.title || ''}</td>
                      <td class='px-4 py-3'>${d.publisher || ''}</td>
                      <td class='px-4 py-3'>${d.year || ''}</td>
                      <td class='px-4 py-3 text-right'><a href='#' class='text-slate-600 hover:underline mr-3'>Sửa</a><a href='#' class='text-rose-600 hover:underline'>Xóa</a></td>
                    </tr>`;
            break;
        case 'patent':
            html = `<tr>
                      <td class='px-4 py-3'>${idx}</td>
                      <td class='px-4 py-3 font-medium'>${d.title || ''}</td>
                      <td class='px-4 py-3'>${d.patent_no || ''}</td>
                      <td class='px-4 py-3'>${d.year || ''}</td>
                      <td class='px-4 py-3 text-right'><a href='#' class='text-slate-600 hover:underline mr-3'>Sửa</a><a href='#' class='text-rose-600 hover:underline'>Xóa</a></td>
                    </tr>`;
            break;
        case 'supervision':
            html = `<tr>
                      <td class='px-4 py-3'>${idx}</td>
                      <td class='px-4 py-3 font-medium'>${d.student_name || ''}</td>
                      <td class='px-4 py-3'>${d.thesis_title || ''}</td>
                      <td class='px-4 py-3'>${d.level || ''}</td>
                      <td class='px-4 py-3'>${d.year || ''}</td>
                      <td class='px-4 py-3 text-right'><a href='#' class='text-slate-600 hover:underline mr-3'>Sửa</a><a href='#' class='text-rose-600 hover:underline'>Xóa</a></td>
                    </tr>`;
            break;
    }
    tb.insertAdjacentHTML('beforeend', html);
}

