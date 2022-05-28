$("#email").keyup(function(){
    const pattern = /^\b[A-Z0-9._%-]+@[A-Z0-9.-]+\.[A-Z]{2,4}\b$/i;
    let email = $(this).val().trim('');
    if(email.length > 0) {
        $.ajax({
            url: "http://localhost:8080/user/findByEmail",
            type: "POST",
            dataType: 'text',
            contentType: false,
            processData: false,
            cache: false,
            data: email,
        }).done(function(msg) {
            if( pattern.test(email) && msg === "OK") {
                $("#email").css({'background-color': 'green'});
            }
            else{
                $("#email").css({'background-color' : 'red'})
            }
        })
    } else {
        $("#username").css({'background-color': "none"});
    }
})

function previewFile() {
    let preview = document.querySelector('img');
    let file    = document.querySelector('input[type=file]').files[0];
    let reader  = new FileReader();

    reader.onloadend = function () {
        preview.src = reader.result;
    }

    if (file) {
        reader.readAsDataURL(file);
    } else {
        preview.src = "";
    }
}

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

$("#submit").on('click',function() {
    let formData = new FormData();
    formData.append("firstName", $("#firstName").val());
    formData.append("lastName", $("#lastName").val());
    formData.append("email", $("#email").val());
    formData.append("password", $("#password").val());
    formData.append("confPassword", $("#confPassword").val());
    formData.append("city", $("#city").val());
    formData.append("image", $("#image")[0].files[0]);

    $.ajax({
        url: "http://localhost:8080/expert/save",
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
                text: 'در حال منتقل شدن به صفحه ورود',
                type: 'success',
                confirmButtonText: 'Cool',
                didOpen: function () {
                    Swal.showLoading()
                    setTimeout(function () {
                        Swal.close()
                    }, 3000)
                }
            })
            window.location = "https://www.aspsnippets.com/";
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

function OnFileValidation() {
    const image = document.getElementById("image");
    if (typeof (image.files) != "undefined") {
        const size = parseFloat(image.files[0].size / (1024 * 1024)).toFixed(2);
        if (size > 0.300) {
            alert('Please select image size less than 300 KB');
            $('#image').val('');
        }
    }
}