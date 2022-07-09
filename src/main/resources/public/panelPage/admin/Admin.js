function openCity(evt, cityName) {
    let i, tabContent, tabLinks;
    tabContent = document.getElementsByClassName("tabContent");
    for (i = 0; i < tabContent.length; i++) {
        tabContent[i].style.display = "none";
    }
    tabLinks = document.getElementsByClassName("tabLinks");
    for (i = 0; i < tabLinks.length; i++) {
        tabLinks[i].className = tabLinks[i].className.replace(" active", "");
    }
    document.getElementById(cityName).style.display = "block";
    evt.currentTarget.className += " active";
}

document.getElementById("defaultOpen").click();

$("#submitServiceName").on('click',function() {
    let formData = new FormData();
    formData.append("services", $("#serviceName").val());

    $.ajax({
        url: "http://localhost:8080/services/save",
        data: formData,
        type: "POST",
        contentType: false,
        dataType: 'text',
        processData: false,
        cache: false,
    }).done(function(msg) {
        if(msg === "OK") {
            Swal.fire({
                title: 'ثبت موفق!',
                confirmButtonText: 'Cool',
                didOpen: function () {
                    Swal.showLoading()
                    setTimeout(function () {
                        Swal.close()
                    }, 3000)
                }
            })
        }
        else
            alert(msg)
    })
        .fail(function() {
            Swal.fire({
                title: 'خطا!',
                text: 'لطفا دوباره امتحان کنید',
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

$("#addSubService").one('click',function() {
    $.ajax({
        url: "http://localhost:8080/services/getAllServices",
        type: "GET",
        dataType: "json",
        success: function (result)
        {
            let toAppend = '';
            $.each(result,function(i,o){
                toAppend += '<option>'+o.id + " " + o.services +'</option>';
            });
            $('#addSubService').append(toAppend);

            $("#addSubService").change(function(){
                if( $(this).val() !== "یک سرویس انتخاب کنید")
                    $('#subServiceId').val($(this).val()[0]);
                else
                    $('#subServiceId').val("");
            });
        },
    });
});

$("#submitSubServiceName").on('click',function() {
    let id = $("#subServiceId").val();
    if(id === ""){
        Swal.fire({
            title: 'لطفا یک سرویس انتخاب کنید',
            confirmButtonText: 'بستن',
        });
        return;
    }
    let formData = new FormData();
    formData.append("id", id);
    formData.append("subServicesName", $("#subServiceName").val());
    formData.append("description", $("#subServiceDescription").val());
    formData.append("minimalPrice", $("#subServicePrice").val());

    $.ajax({
        url: "http://localhost:8080/subServices/save",
        data: formData,
        type: "POST",
        contentType: false,
        dataType: 'text',
        processData: false,
        cache: false,
    }).done(function(msg) {
        if(msg === "OK") {
            $(':input','#subService')
                .not(':button, :submit, :reset, :hidden')
                .val('')
                .removeAttr('checked')
                .removeAttr('selected');
            Swal.fire({
                title: 'ثبت موفق!',
                confirmButtonText: 'Cool',
                didOpen: function () {
                    Swal.showLoading()
                    setTimeout(function () {
                        Swal.close()
                    }, 3000)
                }
            })
        }
        else
            alert(msg)
    })
        .fail(function() {
            Swal.fire({
                title: 'خطا!',
                text: 'لطفا دوباره امتحان کنید',
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

$("#confirmExpert").one('click',function() {
    $.ajax({
        url: "http://localhost:8080/admin/getAllExpertFalse",
        type: "GET",
        dataType: "json",
        success: function (result)
        {
            let toAppend = '';
            $.each(result,function(i,o){
                toAppend += '<option>'+o.id + " " +o.email + " " + o.firstName + " " + o.lastName + '</option>';
            });
            $('#confirmExpert').append(toAppend);

            $("#confirmExpert").change(function(){
                if( $(this).val() !== "لطفا متخصص مورد نظر را برای تایید انتخاب کنید"){
                    $.ajax({
                        url: "http://localhost:8080/admin/confirmExpert/" + $(this).val()[0],
                        type: "GET",
                        dataType: 'text',
                    }).done(function(msg) {
                        if (msg === "OK") {
                            $(':input', '#subService')
                                .not(':button, :submit, :reset, :hidden')
                                .val('')
                                .removeAttr('checked')
                                .removeAttr('selected');
                            Swal.fire({
                                title: 'تایید موفق!',
                                confirmButtonText: 'Cool',
                                didOpen: function () {
                                    Swal.showLoading()
                                    setTimeout(function () {
                                        Swal.close()
                                    }, 3000)
                                }
                            })
                            location.reload(true);
                        }
                        else
                            alert(msg)
                    })
                        .fail(function() {
                            Swal.fire({
                                title: 'خطا!',
                                text: 'لطفا دوباره امتحان کنید',
                                confirmButtonText: 'Cool',
                                didOpen: function () {
                                    Swal.showLoading()
                                    setTimeout(function () {
                                        Swal.close()
                                    }, 2000)
                                }
                            })
                        })
                }
            });
        },
    });
});

$("#addExpertService").one('click',function() {
    $.ajax({
        url: "http://localhost:8080/services/getAllServices",
        type: "GET",
        dataType: "json",
        success: function (result)
        {
            let toAppend = '';
            $.each(result,function(i,o){
                toAppend += '<option>'+o.id + " " + o.services +'</option>';
            });
            $('#addExpertService').append(toAppend);

            $("#addExpertService").change(function(){
                $('#addExpertSubService')
                    .find('option')
                    .remove()
                    .end()
                    .append('<option >لطفا زیر سرویس را انتخاب کنید</option>')
                if( $(this).val() !== "یک سرویس انتخاب کنید") {
                    $.ajax({
                        url: "http://localhost:8080/subServices/getAllSubServices/" + $(this).val()[0],
                        type: "GET",
                        dataType: "json",
                        success: function (result) {
                            let toAppend = '';
                            $.each(result, function (i, o) {
                                toAppend += '<option>' + o.id + " " + o.subServicesName + '</option>';
                            });
                            $('#addExpertSubService').append(toAppend);

                            $("#addExpertSubService").change(function (){
                                if( $(this).val() !== "لطفا زیر سرویس را انتخاب کنید"){
                                    let form = new FormData();
                                    form.append("id",$(this).val()[0]);
                                    form.append("email",$("#emailExpertForAdd").val());
                                    //todo get method instead of post method
                                    $.ajax({
                                        url: "http://localhost:8080/admin/addExpertToSubServices",
                                        type: "POST",
                                        processData: false,
                                        contentType: false,
                                        cache:false,
                                        data: form,
                                        dataType: "text",
                                    }).done(function (msg){
                                        if (msg === "OK") {
                                            $(':input', '#addExpertToSubServices')
                                                .not(':button, :submit, :reset, :hidden')
                                                .val('')
                                                .removeAttr('checked')
                                                .removeAttr('selected');
                                            Swal.fire({
                                                title: 'تایید موفق!',
                                                confirmButtonText: 'Cool',
                                                didOpen: function () {
                                                    Swal.showLoading()
                                                    setTimeout(function () {
                                                        Swal.close()
                                                    }, 3000)
                                                }
                                            })
                                            //location.reload(true);
                                        }
                                    }).fail(function() {
                                        Swal.fire({
                                            title: 'خطا!',
                                            text: 'لطفا دوباره امتحان کنید',
                                            confirmButtonText: 'Cool',
                                        })
                                    })
                                }
                            });
                        }
                    });
                }
            });
        },
    });
});

$("#emailExpertForRemove").blur('click',function() {
    $.ajax({
        url: "http://localhost:8080/admin/showExpertSubServices/" + $(this).val(),
        type: "GET",
        dataType: "json",
        success: function (result) {
            if(result.length === 0){
                alert("this expert doesn't have any services")
            }else {
                let toAppend = '';
                $.each(result, function (i, o) {
                    toAppend += '<option>' + o.id + " " + o.subServicesName + '</option>';
                });
                $('#removeExpertService').append(toAppend);

                $("#removeExpertService").change(function () {
                    if ($(this).val() !== "لطفا سرویس را انتخاب کنید") {
                        let form = new FormData();
                        form.append("id", $(this).val()[0]);
                        form.append("email", $("#emailExpertForRemove").val());
                        //todo get method instead of post method
                        $.ajax({
                            url: "http://localhost:8080/admin/removeExpertSubServices",
                            type: "POST",
                            processData: false,
                            contentType: false,
                            cache: false,
                            data: form,
                            dataType: "text",
                        }).done(function (msg){
                            if (msg === "OK") {
                                $(':input', '#removeExpertService')
                                    .not(':button, :submit, :reset, :hidden')
                                    .val('')
                                    .removeAttr('checked')
                                    .removeAttr('selected');
                                Swal.fire({
                                    title: 'حذف موفق!',
                                    confirmButtonText: 'Cool',
                                    didOpen: function () {
                                        Swal.showLoading()
                                        setTimeout(function () {
                                            Swal.close()
                                        }, 3000)
                                    }
                                })
                                location.reload(true);
                            }
                        }).fail(function() {
                            Swal.fire({
                                title: 'خطا!',
                                text: 'لطفا دوباره امتحان کنید',
                                confirmButtonText: 'بستن',
                            })
                        })
                    }
                });
            }
        },error : function (){
            Swal.fire({
                text: 'این متخصص هیچ سرویس فعالی ندارد',
                confirmButtonText: 'Cool',
            })
        }
    });
});

$('#submitChangePassword').click(function(event){

    let password = $('.newPassword').val();
    let len = password.length;

    if(len < 1) {
        event.preventDefault();
        alert("لطفا رمز ها را وارد کنید");
    }

    if( password !== $('.newConfPassword').val()) {
        alert("لطفا رمز های یکسانی وارد کنید");
        event.preventDefault();
        $('#password').val('');
        $('#confPassword').val('');
    }
});

$("#submitChangePassword").on('click',function() {
    let password = $("#newPassword").val()
    let confPass = $("#newConfPassword").val()
    let formPassword = new FormData();
    formPassword.append("password", password);
    formPassword.append("confPassword", confPass);

    $.ajax({
        url: "http://localhost:8080/admin/updateAdmin",
        data:formPassword,
        type: "POST",
        dataType: "text",
        processData: false,
        contentType: false,
    }).done(function(msg) {
        if(msg === "OK") {
            $(':input','#changePassword')
                .not(':button, :submit, :reset, :hidden')
                .val('')
                .removeAttr('checked')
                .removeAttr('selected');
            Swal.fire({
                text: 'تغییر موفق',
                type: 'success',
                confirmButtonText: 'Cool',
                didOpen: function () {
                    Swal.showLoading()
                    setTimeout(function () {
                        Swal.close()
                    }, 3000)
                }
            })
            location.reload(true);
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

$("#searchUser").click('click',function() {
    if(document.getElementById('customerSearch').checked === true) {
        let formData = new FormData();
        formData.append("firstName", $("#firstName").val());
        formData.append("lastName", $("#lastName").val());
        formData.append("email", $("#email ").val());

        $.ajax({
            url:"http://localhost:8080/customer/gridSearch",
            type:"POST",
            dataType:"json",
            data:formData,
            contentType: false,
            processData: false,
            cache: false,
        }).done(function (msg) {
            alert(msg.length)
        })
    } else {
        let formData = new FormData();
        formData.append("firstName", $("#firstName").val());
        formData.append("lastName", $("#lastName").val());
        formData.append("email", $("#email ").val());
        formData.append("service", $("#service ").val());
        formData.append("stars", $("#stars ").val());

        $.ajax({
            url:"http://localhost:8080/expert/gridSearch",
            type:"POST",
            dataType:"json",
            data:formData,
            contentType: false,
            processData: false,
            cache: false,
        }).done(function (msg) {
            alert(msg.length)
        })
    }
});