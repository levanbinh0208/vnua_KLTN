<script>
    const signUpButton = document.getElementById('signUp');
    const signInButton = document.getElementById('signIn');
    const container = document.getElementById('container');

    signUpButton.addEventListener('click', () => {
    container.classList.add('right-panel-active')
});

    signInButton.addEventListener('click', () => {
    container.classList.remove('right-panel-active')
});
setTimeout(function () {
        var errorDiv = document.getElementById('error-message');
        if (errorDiv) {
        errorDiv.style.display = 'none';
    }
    }, 1000);
</script>