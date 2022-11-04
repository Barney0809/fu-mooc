Toast = {
    success: function (message) {
        Swal.fire({
            position: 'top-end',
            type: 'success',
            title: message,
            showConfirmButton: false,
            timer: 3000
        });
    },

    error: function (message) {
        Swal.fire({
            position: 'top-end',
            type: 'error',
            title: message,
            showConfirmButton: false,
            timer: 3000
        });
    },

    warning: function (message) {
        Swal.fire({
            position: 'top-end',
            type: 'warning',
            title: message,
            showConfirmButton: false,
            timer: 3000
        });
    }
};