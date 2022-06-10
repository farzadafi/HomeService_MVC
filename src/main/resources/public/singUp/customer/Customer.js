$("#submit").on('click',function() {
    let formData = new FormData();
    formData.append("firstName", $("#firstName").val());
    formData.append("lastName", $("#lastName").val());
    formData.append("email", $("#email").val());
    formData.append("password", $("#password").val());
    formData.append("confPassword", $("#confPassword").val());

    $.ajax({
        url: "http://localhost:8080/customer/save",
        data: formData,
        type: "POST",
        contentType: false,
        dataType: 'text',
        processData: false,
        cache: false,
    }).done(function(msg) {
        if(msg === "OK") {
            $(':input','#formExpert')
                .not(':button, :submit, :reset, :hidden')
                .val('')
                .removeAttr('checked')
                .removeAttr('selected');
            Swal.fire({
                title: 'ثبت موفق!',
                text: 'لطفا پس از تایید ایمیل خود که به ایمیل شما ارسال شد وارد سیستم شوید',
                type: 'success',
                confirmButtonText: 'Cool',
                didOpen: function () {
                    Swal.showLoading()
                    setTimeout(function () {
                        Swal.close()
                    }, 5000)
                }
            })
            setInterval(function () {
                window.location = "http://localhost:8080/";
            }, 5000);
        }
        else
            alert(msg)
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

$("#email").keyup(function () {
    const pattern = /^\b[A-Z0-9._%-]+@[A-Z0-9.-]+\.[A-Z]{2,4}\b$/i;
    let email = $(this).val().trim('');
    if (email.length > 0) {
        $.ajax({
            url: "http://localhost:8080/user/findByEmail",
            type: "POST",
            dataType: 'text',
            contentType: false,
            processData: false,
            cache: false,
            data: email,
        }).done(function (msg) {
            if (pattern.test(email) && msg === "OK") {
                $("#email").css({'background-color': 'green'});
            } else {
                $("#email").css({'background-color': 'red'})
            }
        })
    } else {
        $("#username").css({'background-color': "none"});
    }
});

$('#submit').click(function(event){

    let password = $('.password').val();
    let len = password.length;

    if(len < 1) {
        event.preventDefault();
        alert("لطفا رمز ها را وارد کنید");
    }

    if( password !== $('.confPassword').val()) {
        alert("لطفا رمز های یکسانی وارد کنید");
        event.preventDefault();
        $('#password').val('');
        $('#confPassword').val('');
    }
});