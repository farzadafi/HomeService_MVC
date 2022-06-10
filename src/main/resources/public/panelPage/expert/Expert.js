function openDiv(evt, divName) {
    let i, tabContent, tabLinks;
    tabContent = document.getElementsByClassName("tabContent");
    for (i = 0; i < tabContent.length; i++) {
        tabContent[i].style.display = "none";
    }
    tabLinks = document.getElementsByClassName("tabLinks");
    for (i = 0; i < tabLinks.length; i++) {
        tabLinks[i].className = tabLinks[i].className.replace(" active", "");
    }
    document.getElementById(divName).style.display = "block";
    evt.currentTarget.className += " active";
}

$("#placeAnOfferButton").one('click',function() {
    $.ajax({
        url: "http://localhost:8080/order/viewStartedOrderByCity",
        type: "GET",
        dataType: "json",
        success: function (response)
        {
            if(response.length === 0){
                alert("شما هیچ سفارشی ندارید!")
                return;
            }
            $("#offerTableRow").find("tr:gt(0)").remove();
            for(let i = 0; i < response.length; i++) {
                $('#offerTableRow').append('<tr><td>' + response[i]['id'] +
                    '</td><td>' + response[i]['proposedPrice']  +
                    '</td><td>' + response[i]['description']  +
                    '</td><td>' + response[i]['date'].split('T')[0] +
                    '</td><td>' + response[i]['street'] +
                    '</td><td>' + '<button  data-id="' + response[i]['id'] + '" class="btn btn-warning btnPlaceAnOffer" >انتخاب</button>');
            }
        },fail:(function (request){
            Swal.fire({
                title: 'خطا!',
                text: request.responseText,
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
});

$('#offerTableRow').on('click','.btnPlaceAnOffer',function(){
    let idVal=$(this).data('id');
    $("#orderIdPlaceAnOffer").val(idVal);
    $("#modalPlaceAnOffer").show();
});

$('#closeModalPlaceAnOffer').on('click',function (){
    $(':input', '#modalPlaceAnOffer')
        .not(':button, :submit, :reset, :hidden')
        .val('')
        .removeAttr('checked')
        .removeAttr('selected');
    $("#modalPlaceAnOffer").hide();
})

$("#savePlaceAnOffer").on('click', function () {
    const json = {
        "proposedPrice": $("#proposedPricePlaceAnOffer").val(),
        "durationWork": $("#durationWorkPlaceAnOffer").val(),
        "startTime": $("#startTimePlaceAnOffer").val()
    }

    $.ajax({
        url: "http://localhost:8080/offer/placeAnOffer/" + $("#orderIdPlaceAnOffer").val(),
        type: "POST",
        data: JSON.stringify(json),
        contentType: "application/json; charset=utf-8",
        dataType: 'text',
        success: function (response) {
            if (response === 'OK') {
                $(':input', '#modalPlaceAnOffer')
                    .not(':button, :submit, :reset, :hidden')
                    .val('')
                    .removeAttr('checked')
                    .removeAttr('selected');
                $("#modalPlaceAnOffer").hide();
                Swal.fire({
                    title: 'پیشنهاد شما با موفقیت ثبت شد!',
                    didOpen: function () {
                        Swal.showLoading()
                        setTimeout(function () {
                            Swal.close()
                        }, 3000)
                    }
                })
            }
        }, fail: (function (request) {
            Swal.fire({
                title: 'خطا!',
                text: request.responseText,
                didOpen: function () {
                    Swal.showLoading()
                    setTimeout(function () {
                        Swal.close()
                    }, 2000)
                }
            })
        }),error:(function (error){
            Swal.fire({
                title: 'خطا!',
                text: error.responseText,
                didOpen: function () {
                    Swal.showLoading()
                    setTimeout(function () {
                        Swal.close()
                    }, 2000)
                }
            })
        })
    });
});

$("#startAnOrderButton").on('click',function() {
    $.ajax({
        url: "http://localhost:8080/offer/viewAcceptedOffer",
        type: "GET",
        dataType: "json",
        success: function (response)
        {
            if(response.length === 0){
                alert("شما هیچ پیشنهاد تایید شده ای ندارید!")
                return;
            }
            $("#tableStartOrderRowForExpert").find("tr:gt(0)").remove();
            for(let i = 0; i < response.length; i++) {
                $('#tableStartOrderRowForExpert').append('<tr><td>' + response[i]['orderId'] +
                    '</td><td>' + response[i]['proposedPrice']  +
                    '</td><td>' + response[i]['durationWork']  +
                    '</td><td>' + response[i]['startTime'] +
                    '</td><td>' + '<button  data-id="' + response[i]['orderId'] + '" class="btn btn-info btnDetailsOrderExpert" >جزئیات</button>' +
                    '</td><td>' + '<button  data-id="' + response[i]['id'] + '" class="btn btn-success btnStartOrderExpert" >انتخاب</button>');
            }
        },fail:(function (request){
            Swal.fire({
                title: 'خطا!',
                text: request.responseText,
                didOpen: function () {
                    Swal.showLoading()
                    setTimeout(function () {
                        Swal.close()
                    }, 2000)
                }
            })
        }),error:(function (request){
            Swal.fire({
                title: 'خطا!',
                text: request.responseText,
                didOpen: function () {
                    Swal.showLoading()
                    setTimeout(function () {
                        Swal.close()
                    }, 3000)
                }
            })
        })
    });
});

$('#tableStartOrderRowForExpert').on('click','.btnDetailsOrderExpert',function(){
    let idVal=$(this).data('id');
    const newLine = "\r\n";
    $.ajax({
        url: "http://localhost:8080/order/viewOrderById/" + idVal,
        type: "GET",
        dataType: "json",
        success: function (response) {
            Swal.fire({
                text: "تاریخ:" + response['date'].split('T')[0] + newLine +
                    "||توضیحات:" + response['description'] +
                    "||خیابان:" + response['street'] +
                    "||کوچه:" + response['alley'] +
                    "||شماره خانه" + response['houseNumber'],
                confirmButtonText: 'بستن',
            })
        },fail:(function (request){
            alert(request.responseText)
        }),error:(function (request){
            alert(request.responseText)
        })
    });
});

$('#tableStartOrderRowForExpert').on('click','.btnStartOrderExpert',function(){
    let idVal=$(this).data('id');
    $.ajax({
        url: "http://localhost:8080/order/startOrder/" + idVal,
        type: "GET",
        dataType: "text",
        success: function (response) {
            if (response === 'OK') {
                Swal.fire({
                    title: 'وضعیت این سفارش به شروع شده تغییر پیدا کرد!',
                    didOpen: function () {
                        Swal.showLoading()
                        setTimeout(function () {
                            Swal.close()
                        }, 3000)
                    }
                })
                setInterval(function() {
                    location.reload();
                }, 3000);
            }
        },fail:(function (request){
            alert(request.responseText)
        }),error:(function (error){
            Swal.fire({
                title: 'خطا!',
                text: error.responseText,
                didOpen: function () {
                    Swal.showLoading()
                    setTimeout(function () {
                        Swal.close()
                    }, 2000)
                }
            })
        })
    });
});