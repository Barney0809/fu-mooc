const Toast = Swal.mixin({
    toast: true,
    position: 'top-end',
    showConfirmButton: false,
    timer: 3000,
})

toast = {
    success: function (message) {
        Toast.fire({
            type: 'success',
            title: message
        });
    },

    error: function (message) {
        Toast.fire({
            type: 'error',
            title: message
        });
    },

    warning: function (message) {
        Toast.fire({
            type: 'warning',
            title: message
        });
    }
};