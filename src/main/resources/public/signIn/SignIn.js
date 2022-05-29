
$("#submit").on('click',function() {
    let formData = new FormData();
    formData.append("email", $("#email").val());
    formData.append("password", $("#password").val());

    $.ajax({
        url: "http://localhost:8080/user/login",
        data: formData,
        type: "POST",
        contentType: false,
        dataType: 'text',
        processData: false,
        cache: false,
    }).done(function(msg) {
        if(msg === "ERROR") {
            $('#email').val('');
            $('#password').val('');
            Swal.fire({
                title: 'خطا!',
                text: 'نام کاربری یا رمز را اشتباه وارد کرده اید',
                confirmButtonText: 'Cool',
                didOpen: function () {
                    Swal.showLoading()
                    setTimeout(function () {
                        Swal.close()
                    }, 3000)
                }
            })
        }
        else if(msg === "EXPERT_FALSE"){
            $('#email').val('');
            $('#password').val('');
            Swal.fire({
                title: 'خطا!',
                text: 'متاسفانه توسط ادمین تایید نشده اید',
                confirmButtonText: 'Cool',
                didOpen: function () {
                    Swal.showLoading()
                    setTimeout(function () {
                        Swal.close()
                    }, 3000)
                }
            })
        }
        else if(msg === "ADMIN"){
            $('#email').val('');
            $('#password').val('');
            Swal.fire({
                title: 'خوش آمدید!',
                text: 'در حال انتقال به پنل کاربری',
                type: 'success',
                confirmButtonText: 'Cool',
                didOpen: function () {
                    Swal.showLoading()
                    setTimeout(function () {
                        Swal.close()
                    }, 3000)
                }
            })
            window.location = "https://www.google.com";
        }
        else if(msg === "EXPERT"){
            $('#email').val('');
            $('#password').val('');
            Swal.fire({
                title: 'خوش آمدید!',
                text: 'در حال انتقال به پنل کاربری',
                confirmButtonText: 'Cool',
                didOpen: function () {
                    Swal.showLoading()
                    setTimeout(function () {
                        Swal.close()
                    }, 3000)
                }
            })
            window.location = "https://www.soft98.ir/";
        }
        else if(msg === "CUSTOMER"){
            $('#email').val('');
            $('#password').val('');
            Swal.fire({
                title: 'خوش آمدید!',
                text: 'در حال انتقال به پنل کاربری',
                confirmButtonText: 'Cool',
                didOpen: function () {
                    Swal.showLoading()
                    setTimeout(function () {
                        Swal.close()
                    }, 3000)
                }
            })
            window.location = "https://www.bing.com/";
        }
        else {
            $('#email').val('');
            $('#password').val('');
            Swal.fire({
                title: 'خطا!',
                text: 'لطفا دوباره امتحان کنید!',
                confirmButtonText: 'Cool',
                didOpen: function () {
                    Swal.showLoading()
                    setTimeout(function () {
                        Swal.close()
                    }, 3000)
                }
            })
        }
    })
        .fail(function() {
            Swal.fire({
                title: 'خطا!',
                text: 'لطفا دوباره امتحان کنید',
                type: 'success',
                confirmButtonText: 'Cool',
                didOpen: function () {
                    Swal.showLoading()
                    setTimeout(function () {
                        Swal.close()
                    }, 2000)
                }
            })
        })
});