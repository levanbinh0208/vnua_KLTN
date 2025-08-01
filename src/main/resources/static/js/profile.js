<script>
    function filterUsers() {
    const nameVal = document.getElementById("nameInput").value.toLowerCase();
    const deptVal = document.getElementById("deptInput").value.toLowerCase();
    const roleVal = document.getElementById("roleInput").value.toLowerCase();

    const cards = document.querySelectorAll(".user-card");

    cards.forEach(card => {
    const name = card.querySelector(".username")?.textContent.toLowerCase() || "";
    const dept = card.querySelector(".email")?.textContent.toLowerCase() || "";
    const role = card.querySelector(".remark")?.textContent.toLowerCase() || "";

    const matches =
    name.includes(nameVal) &&
    dept.includes(deptVal) &&
    role.includes(roleVal);

    card.style.display = matches ? "flex" : "none";
});
}
</script>