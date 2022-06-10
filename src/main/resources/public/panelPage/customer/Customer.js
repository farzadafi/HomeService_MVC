let map = L.map('mapDiv').setView([30, 56], 8);
L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '&copy; <a href="https://openstreetmap.org/copyright">OpenStreetMap</a> contributors'
}).addTo(map);


map.on('click', function(e) {
    doStuff(e);
});


function doStuff(e) {
    const far = e.latlng;
    const x = e.layerPoint.x;
    const y = e.layerPoint.y;
    $("#lat").val(x);
    $("#tal").val(y);
    console.log([x, y]);

    const pointXY = L.point(x, y);
    console.log("Point in x,y space: " + pointXY);

    const pointLatLng = map.layerPointToLatLng(pointXY);
    console.log("Point in lat,lng space: " + pointLatLng);
    let location = pointLatLng;

    location = location.toString().replace(/[a-zA-Z() ]+/g,"");

    let latLocation = location.toString().split(',')[0];
    let lonLocation = location.toString().split(',')[1];

    obtainAddress(latLocation,lonLocation);

}

$(".enter").mousemove(function () {
    const elWidth = 250;

    const position = event.pageX - $(this).offset().left;
    let percent = Math.round((position / elWidth) * 100);
    while (true) {
        if (percent % 20 !== 0)
            percent++;
        else
            break;
    }
    $(".enter .star-rating-on").css({width: percent + "%"})
})

$(".enter").click(function() {
    const elWidth = 250;
    let position = event.pageX - $(this).offset().left;
    if(position > 0 && position < 45 )
        position = 1;
    if(position > 45 && position < 90 )
        position = 2;
    if(position > 90 && position < 135 )
        position = 3;
    if(position > 135 && position < 180 )
        position = 4;
    if(position > 180 && position < 225 )
        position = 5;
    $("#stars").val(position)
})

function obtainAddress(latLocation,lonLocation){
    $.ajax({
        url:`https://map.ir/reverse/fast-reverse/?lat=${latLocation}&lon=${lonLocation}`,
        beforeSend: function(request) {
            request.setRequestHeader('x-api-key', 'eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjcwMmMyMGMzZTk4MzZjMzQzYTk4NjNmYzcwMDNlOWE1ZDhjZDQyOTczYjU0MTJhNjFkMTcxMDRlMTNlZmZkMWI2OTk5NTEyZDY4MWIwMmQ4In0.eyJhdWQiOiIxODIzOSIsImp0aSI6IjcwMmMyMGMzZTk4MzZjMzQzYTk4NjNmYzcwMDNlOWE1ZDhjZDQyOTczYjU0MTJhNjFkMTcxMDRlMTNlZmZkMWI2OTk5NTEyZDY4MWIwMmQ4IiwiaWF0IjoxNjU0NjIwNjk2LCJuYmYiOjE2NTQ2MjA2OTYsImV4cCI6MTY1NzEyNjI5Niwic3ViIjoiIiwic2NvcGVzIjpbImJhc2ljIl19.tNI6qo9c6bK8kjdIgbInX7k2p2wBX9JrOyRs2idEVMcVpI0ltvJhoaLC4SQSUzqssyoYSUtDjcIDy3ANN4W__0MVvyIAryqJE5dRiCxCpuTIoi8elBvbUbsptF5-GDdZczmgcUHB1uaDW9doFYDHBtEFZR_7Ir-VTSwQc-sZ84GRhcYnlAKNinV-bh0fbmnKJnRra8DpyXHYYp-IkUEFiJQCz0BXWwcwuvdx76dBphBQwQjcW51mtNKfqL0n3ubqkqJdPL8-YuGRx0Sw3CzaCvhk5sHPyyWVb5Zl5FehGwgurnQJWvdNY7gWUT9sOB-Js4GbLaQJuKafA0eIt6FGQw');
            request.setRequestHeader('content-type', 'application/json');
        },
        type:'GET',
        success:function (data){
            alert(data.address)
            $("#cityForOrder").val(data.address.split('، ')[2])
            $("#streetForOrder").val(data.address.split('، ')[6])
        },error:function (result){
            Swal.fire({
                title:"Error",
                text:result.responseText,
                confirmButtonText: 'close',
            })
        }
    })
}

$("#searchCustomerButton").on('click',function (){
    $("#TableOrderRowForSearch").empty();
    const value = $('input[name="radio"]:checked').val();
    $.ajax({
        url: "http://localhost:8080/order/showHistory/" + value,
        type: "GET",
        dataType: "json",
        success: function (response)
        {
            $("#TableOrderRowForSearch").find("tr:gt(0)").remove();
            for(let i = 0; i < response.length; i++) {
                $('#TableOrderRowForSearch').append('<tr><td>' + response[i]['id'] +
                    '</td><td>' + response[i]['proposedPrice']  +
                    '</td><td>' + response[i]['description']  +
                    '</td><td>' + response[i]['date'].split('T')[0] +
                    '</td><td>' + response[i]['street'] );
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
                    }, 2000)
                }
            })
        })
    });
});

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

$("#placeAnOrderService").one('click', function () {
    $.ajax({
        url: "http://localhost:8080/services/getAllServices",
        type: "GET",
        dataType: "json",
        success: function (result) {
            let toAppend = '';
            $.each(result, function (i, o) {
                toAppend += '<option>' + o.id + " " + o.services + '</option>';
            });
            $('#placeAnOrderService').append(toAppend);

            $("#placeAnOrderService").change(function () {
                $('#placeAnOrderSubService')
                    .find('option')
                    .remove()
                    .end()
                    .append('<option >لطفا زیر سرویس را انتخاب کنید</option>')
                if ($(this).val() !== "یک سرویس انتخاب کنید") {
                    $.ajax({
                        url: "http://localhost:8080/subServices/getAllSubServices/" + $(this).val()[0],
                        type: "GET",
                        dataType: "json",
                        success: function (result) {
                            let toAppend = '';
                            $.each(result, function (i, o) {
                                toAppend += '<option>' + o.id + " " + o.subServicesName + " " + o.minimalPrice + '</option>';
                            });
                            $('#placeAnOrderSubService').append(toAppend);

                            $("#placeAnOrderSubService").change(function () {
                                if ($(this).val() !== "لطفا زیر سرویس را انتخاب کنید") {
                                    $("#proposedPriceForOrder").attr("placeholder", "حداقل قیمت پیشنهادی " + $(this).val().split(' ')[2]);
                                    $("#subServicesId").val($(this).val()[0] + " " + $(this).val().split(' ')[2] )
                                }
                            });
                        }
                    });
                }
            });
        },
    });
});

$("#submitPlaceAnOrder").on('click',function() {
    let value = $("#subServicesId").val();
    let subServicesId = value.split(' ')[0];
    if(subServicesId === ""){
        Swal.fire({
            title: 'لطفا یک سرویس انتخاب کنید',
            confirmButtonText: 'بستن',
        });
        return;
    }
    let json = {
        "id":subServicesId,
        "proposedPrice":$("#proposedPriceForOrder").val() ,
        "description":$("#descriptionForOrder").val() ,
        "date":$("#workDateForOrder").val() ,
        "city":$("#cityForOrder").val() ,
        "street":$("#streetForOrder").val() ,
        "alley":$("#alleyForOrder").val() ,
        "houseNumber": $("#houseNumberForOrder").val() ,
    }
    if(json.proposedPrice < value.split(' ')[1]){
        Swal.fire({
            title: 'خطا!',
            text: 'قیمت پیشنهادی باید از حداقل مشخص شده بیشتر باشد',
            confirmButtonText: 'Cool',
            didOpen: function () {
                Swal.showLoading()
                setTimeout(function () {
                    Swal.close()
                }, 2000)
            }
        })
        return;
    }


    $.ajax({
        url: "http://localhost:8080/order/placeAnOrder/" + subServicesId,
        data:JSON.stringify(json),
        type: "POST",
        dataType: 'text',
        contentType: 'application/json',
    }).done(function(msg) {
        if(msg === "OK") {
            $(':input','#placeAnOrder')
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
        .error(function (request){
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

$("#showOfferButton").one('click',function() {
    $.ajax({
        url: "http://localhost:8080/order/viewExpertSelectionOrder",
        type: "GET",
        dataType: "json",
        success: function (response)
        {
            if(response.length === 0){
                alert("شما هیچ سفارشی در وضعیت انتخاب پیشنهاد ندارید!")
                return;
            }
            $("#TableOrderRowForCustomer").find("tr:gt(0)").remove();
            for(let i = 0; i < response.length; i++) {
                $('#TableOrderRowForCustomer').append('<tr><td>' + response[i]['id'] +
                    '</td><td>' + response[i]['proposedPrice']  +
                    '</td><td>' + response[i]['description']  +
                    '</td><td>' + response[i]['date'].split('T')[0] +
                    '</td><td>' + response[i]['street'] +
                    '</td><td>' + '<button  data-id="' + response[i]['id'] + '" class="btn btn-warning btnSelectAnOffer" >انتخاب</button>');
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
                    }, 2000)
                }
            })
        })
    });
});

$('#TableOrderRowForCustomer').on('click','.btnSelectAnOffer',function(){
    let idVal=$(this).data('id');
    $.ajax({
        url: "http://localhost:8080/offer/viewOffer/" + idVal,
        type: "GET",
        dataType: "json",
        success: function (response)
        {
            if(response.length === 0){
                alert("متاسفانه هیچ پیشنهادی ثبت شده است!")
                return;
            }
            $("#TableOfferRowForCustomer").find("tr:gt(0)").remove();
            for(let i = 0; i < response.length; i++) {
                $('#TableOfferRowForCustomer').append('<tr><td>' + response[i]['id'] +
                    '</td><td>' + response[i]['proposedPrice']  +
                    '</td><td>' + response[i]['durationWork']  +
                    '</td><td>' + response[i]['startTime'] +
                    '</td><td>' + '<button  data-id="' + response[i]['id'] + "," + idVal + '" class="btn btn-warning btnFinalSelectAnOffer" >انتخاب</button>');
            }
            $("#modalSelectAnOffer").show();
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
                    }, 2000)
                }
            })
        })
    });
});

$('#closeModalSelectAnOffer').on('click',function (){
    $("#modalSelectAnOffer").hide();
    $("#TableOfferRowForCustomer").reload();
    location.reload(true);
})

$('#TableOfferRowForCustomer').on('click','.btnFinalSelectAnOffer',function() {
    let idVal = $(this).data('id');
    let offerId = idVal.split(',')[0];
    let orderId = idVal.split(',')[1];
    $.ajax({
        url: "http://localhost:8080/offer/selectOffer/" + orderId + "/" + offerId,
        type: "GET",
        dataType: "text",
        success: function (response) {
            if(response === 'OK'){
                Swal.fire({
                    title: 'ثبت موفق',
                    didOpen: function () {
                        Swal.showLoading()
                        setTimeout(function () {
                            Swal.close()
                        }, 2000)
                    }
                })
                location.reload(true);
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
                    }, 2000)
                }
            })
        })
    });
});

$("#showStartedOrderButton").one('click',function() {
    $.ajax({
        url: "http://localhost:8080/order/viewOrderForDone",
        type: "GET",
        dataType: "json",
        success: function (response)
        {
            if(response.length === 0){
                alert("شما هیچ سفارشی در وضعیت شروع شده ندارید!")
                return;
            }
            $("#TableStartedOrderRowForCustomer").find("tr:gt(0)").remove();
            for(let i = 0; i < response.length; i++) {
                $('#TableStartedOrderRowForCustomer').append('<tr><td>' + response[i]['id'] +
                    '</td><td>' + response[i]['proposedPrice']  +
                    '</td><td>' + response[i]['description']  +
                    '</td><td>' + response[i]['date'].split('T')[0] +
                    '</td><td>' + response[i]['street'] +
                    '</td><td>' + '<button  data-id="' + response[i]['id'] + '" class="btn btn-warning btnSelectAnOrderForPaid" >انتخاب</button>');
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
                    }, 2000)
                }
            })
        })
    });
});

$('#TableStartedOrderRowForCustomer').on('click','.btnSelectAnOrderForPaid',function() {
    let idVal = $(this).data('id');
    $.ajax({
        url: "http://localhost:8080/order/doneOrder/" + idVal,
        type: "GET",
        dataType: "text",
        success: function (response) {
            if(response === 'OK'){
                Swal.fire({
                    title: 'ثبت موفق',
                    didOpen: function () {
                        Swal.showLoading()
                        setTimeout(function () {
                            Swal.close()
                        }, 2000)
                    }
                })
                location.reload(true);
            }else{
                Swal.fire({
                    title:"ثبت موفق!",
                    text: response,
                    confirmButtonText:'بستن'
                })
                setInterval(function() {
                    location.reload();
                },10000);
            }
        },fail:(function (request){
            Swal.fire({
                title: 'خطا!',
                text: request,
                didOpen: function () {
                    Swal.showLoading()
                    setTimeout(function () {
                        Swal.close()
                    }, 5000)
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
                    }, 2000)
                }
            })
        })
    });
});

$("#OrderForPayButton").one('click',function() {
    let price ;
    $.ajax({
        url: "http://localhost:8080/order/viewOrderForPaid",
        type: "GET",
        dataType: "json",
        success: function (response)
        {
            if(response.length === 0){
                alert("شما هیچ سفارشی در وضعیت قابل پرداخت ندارید!")
                return;
            }
            $("#TableOrderRowForPay").find("tr:gt(0)").remove();
            for(let i = 0; i < response.length; i++) {
                $.ajax({
                    url:"http://localhost:8080/offer/priceWithOrderId/" + response[i]['id'],
                    type:'GET',
                    dataType:'text',
                    success:function (orderResponse){
                        $("#price").val(orderResponse);
                    }
                })
                alert(price)
                $("#price").val(function(index, curValue){
                    price = curValue;
                })
                $('#TableOrderRowForPay').append('<tr><td>' + response[i]['id'] +
                    '</td><td>' + price +
                    '</td><td>' + response[i]['description']  +
                    '</td><td>' + response[i]['date'].split('T')[0] +
                    '</td><td>' + response[i]['street'] +
                    '</td><td>' + '<button  data-id="' + response[i]['id'] + '" class="btn btn-success btnSelectAnOrderForAccountPaid" >انتخاب</button>' +
                    '</td><td>' + '<button  data-id="' + response[i]['id'] + "," + price + '" class="btn btn-info btnSelectAnOrderForOnlinePaid" >انتخاب</button>');
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
                    }, 2000)
                }
            })
        })
    });
});

$('#TableOrderRowForPay').on('click','.btnSelectAnOrderForAccountPaid',function() {
    let idVal = $(this).data('id');
    $.ajax({
        url: "http://localhost:8080/order/PaidOrder/" + idVal,
        type: "GET",
        dataType: "text",
        success: function (response) {
            if(response === 'OK') {
                Swal.fire({
                    title: 'ثبت موفق',
                    didOpen: function () {
                        Swal.showLoading()
                        setTimeout(function () {
                            Swal.close()
                        }, 3000)
                    }
                })
                setInterval(function () {
                    location.reload();
                }, 3000);
            }
        },fail:(function (request){
            Swal.fire({
                title: 'خطا!',
                text: request,
                didOpen: function () {
                    Swal.showLoading()
                    setTimeout(function () {
                        Swal.close()
                    }, 5000)
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
                    }, 2000)
                }
            })
        })
    });
});

$('#TableOrderRowForPay').on('click', '.btnSelectAnOrderForOnlinePaid', function () {
    let idVal = $(this).data('id');
    let orderId = idVal.split(',')[0];
    let price = idVal.split(',')[1];
    window.open('payment.html?orderId='+orderId+'&&price='+price);
    location.reload(true);
});

$("#placeACommentButton").one('click',function() {
    let price ;
    $.ajax({
        url: "http://localhost:8080/order/viewPaidOrder",
        type: "GET",
        dataType: "json",
        success: function (response)
        {
            $("#TableOrderRowForComment").find("tr:gt(0)").remove();
            for(let i = 0; i < response.length; i++) {
                $('#TableOrderRowForComment').append('<tr><td>' + response[i]['id'] +
                    '</td><td>' + response[i]['proposedPrice'] +
                    '</td><td>' + response[i]['description'] +
                    '</td><td>' + response[i]['date'].split('T')[0] +
                    '</td><td>' + response[i]['street'] +
                    '</td><td>' + '<button  data-id="' + response[i]['id'] + '" class="btn btn-info btnSelectAnOrderForComment" >انتخاب</button>');
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
                    }, 2000)
                }
            })
        })
    });
});

$('#TableOrderRowForComment').on('click', '.btnSelectAnOrderForComment', function () {
    let idVal = $(this).data('id');
    $("#orderIdInputComment").val(idVal);
    $("#modalPlaceAComment").show();
});

$('#closeModalPlaceAComment').on('click',function (){
    $(':input', '#modalPlaceAComment')
        .not(':button, :submit, :reset, :hidden ')
        .val('')
        .removeAttr('checked')
        .removeAttr('selected');
    $("#modalPlaceAComment").hide();
})

$("#submitPlaceACommentButton").on('click',function (){
    const orderId = $("#orderIdInputComment").val();
    const stars = $("#stars").val();
    const comment = $("#textCommentInput").val();
    let json = {
        "stars":stars,
        "comment":comment
    }
    $.ajax({
        url: "http://localhost:8080/comment/save/" + orderId,
        data:JSON.stringify(json),
        type: "POST",
        dataType: 'text',
        contentType: 'application/json',
    }).done(function(msg) {
        if(msg === "OK") {
            $(':input', '#modalPlaceAComment')
                .not(':button, :submit, :reset, :hidden ')
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
    })
        .error(function (request){
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
})