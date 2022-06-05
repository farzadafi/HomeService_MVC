

function getTimeRemaining(endTime) {
    const t = Date.parse(endTime) - Date.parse(new Date());
    const seconds = Math.floor((t / 1000) % 60);
    const minutes = Math.floor((t / 1000 / 60) % 60);
    const hours = Math.floor((t / (1000 / 60 / 60)) % 24);
    const days = Math.floor(t / ((1000 / 60 / 60) * 24));
    return {
        total: t,
        days: days,
        hours: hours,
        minutes: minutes,
        seconds: seconds
    };
}

function initializeClock(id, endTime) {
    const clock = document.getElementById(id);
    const daysSpan = clock.querySelector(".days");
    const hoursSpan = clock.querySelector(".hours");
    const minutesSpan = clock.querySelector(".minutes");
    const secondsSpan = clock.querySelector(".seconds");

    function updateClock() {
        const t = getTimeRemaining(endTime);
        daysSpan.innerHTML = t.days;
        hoursSpan.innerHTML = ("0" + t.hours).slice(-2);
        minutesSpan.innerHTML = ("0" + t.minutes).slice(-2);
        secondsSpan.innerHTML = ("0" + t.seconds).slice(-2);

        if (t.total <= 0) {
            clearInterval(timeInterval);
        }
    }

    updateClock();
    let timeInterval = setInterval(updateClock, 1000);
}

const deadline = new Date(Date.parse(new Date()) + 2 * 3 * 10 * 10 * 1000);

if (new Date() > deadline) {
    alert("COUNTDOWN COMPLETE! \n Some Call to Action!!!");
}

initializeClock("clockDiv", deadline);


window.onload = function onLoadPage() {
    setTimeout("window.close()",600000)
    const url = location.search.substring(1);
    const farReplace = url.replace(/[^&0-9 ]/g, '');
    const orderId = farReplace.split('&&')[0]
    const price = farReplace.split('&&')[1]
    $("#priceOrder").val(price);
    $("#orderId").val(orderId);
    $("#priceForPay").html("مبلغ قابل پرداخت " + price);
}

$("#creditCardNumber1,#creditCardNumber2,#creditCardNumber3,#creditCardNumber4").blur(function () {
    const number = $(this).val();
    if (number.length > 4 || number.length < 4) {
        Swal.fire({
            text: 'شما باید چهار عدد وارد کنید',
            confirmButtonText: 'بستن'
        });
        $(this).val('')
    }
    for (let i = 0; i < number.length; i++) {
        if (!$.isNumeric(number[i])) {
            Swal.fire({
                text: 'فقط عدد مجاز می باشد',
                confirmButtonText: 'بستن',
            })
            $(this).val('')
        }
    }
});

$("#expireYear").blur(function () {
    const number = $(this).val();
    if (number.length > 2 || number.length < 2) {
        Swal.fire({
            text: 'شما باید دو عدد وارد کنید',
            confirmButtonText: 'بستن'
        });
        $(this).val('')
    }
    for (let i = 0; i < number.length; i++) {
        if (!$.isNumeric(number[i])) {
            Swal.fire({
                text: 'فقط عدد مجاز می باشد',
                confirmButtonText: 'بستن',
            })
            $(this).val('')
        }
    }
});

$("#expireMonth").blur(function () {
    const number = $(this).val();
    if (number.length > 2 || number.length < 1) {
        Swal.fire({
            text: 'شما باید عددی بین ۱ تا دوازده وارد کنید',
            confirmButtonText: 'بستن'
        });
        $(this).val('')
    }
    for (let i = 0; i < number.length; i++) {
        if (!$.isNumeric(number[i])) {
            Swal.fire({
                text: 'فقط عدد مجاز می باشد',
                confirmButtonText: 'بستن',
            })
            $(this).val('')
        }
    }
    if (number > 12 || number < 1) {
        Swal.fire({
            text: 'لطفا عددی بین ۱ تا ۱۲ وارد کنید',
            confirmButtonText: 'بستن'
        });
        $(this).val('')
    }
});

$("#cvv2").blur(function () {
    const number = $(this).val();
    if (number.length > 4 || number.length < 3) {
        Swal.fire({
            text: 'عدد سه رقمی یا چهار رقمی مجاز می باشد',
            confirmButtonText: 'بستن'
        });
        $(this).val('')
    }
    for (let i = 0; i < number.length; i++) {
        if (!$.isNumeric(number[i])) {
            Swal.fire({
                text: 'فقط عدد مجاز می باشد',
                confirmButtonText: 'بستن',
            })
            $(this).val('')
        }
    }
});

$("#2password").blur(function () {
    const number = $(this).val();
    if (number.length < 4) {
        Swal.fire({
            text: 'رمز دوم باید بزرگ تر از ۴ رقم باشد',
            confirmButtonText: 'بستن'
        });
        $(this).val('')
    }
    for (let i = 0; i < number.length; i++) {
        if (!$.isNumeric(number[i])) {
            Swal.fire({
                text: 'فقط عدد مجاز می باشد',
                confirmButtonText: 'بستن',
            })
            $(this).val('')
        }
    }
});

$("#submitPayment").on('click',function() {
    const captchaResponse = grecaptcha.getResponse();
    if(captchaResponse === null || captchaResponse === ""){
        Swal.fire({
            title:"خطا!",
            text:"لطفا الگو کپچا را به درستی وارد کنید",
            didOpen: function () {
                Swal.showLoading()
                setTimeout(function () {
                    Swal.close()
                }, 3000)
            }
        })
        return;
    }
    let form = new FormData();
    let creditCard = $("#creditCardNumber1").val() ;
    creditCard += $("#creditCardNumber2").val();
    creditCard += $("#creditCardNumber3").val();
    creditCard += $("#creditCardNumber4").val();

    form.append("price", $("#priceOrder").val());
    form.append("creditCardNumber" , creditCard);
    form.append("monthExpire" , $("#expireMonth").val());
    form.append("yearExpire" ,  $("#expireYear").val());
    form.append("cvv2" , $("#cvv2").val());
    form.append("secondPassword" , $("#2password").val());
    if(form.get("price"). length === 0 || form.get("creditCardNumber").length === 0 || form.get("monthExpire").length === 0 ||
        form.get("yearExpire").length === 0 || form.get("cvv2").length === 0 || form.get("secondPassword").length === 0){
        Swal.fire({
            title:"خطا!",
            text:"لطفا تمامی اطلاعات را وارد کنید",
            didOpen: function () {
                Swal.showLoading()
                setTimeout(function () {
                    Swal.close()
                }, 3000)
            }
        })
        return;
    }

    $.ajax({
        url: "http://localhost:8080/order/onlinePayment/" + $("#orderId").val(),
        data: form,
        type: "POST",
        contentType: false,
        dataType: 'text',
        headers: {
            "captcha-response": captchaResponse
        },
        processData: false,
        cache: false,
        success: function (msg) {
            if (msg === "OK") {
                Swal.fire({
                    title: 'پرداخت موفق',
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
        }, fail: function (request) {
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
        }, error: function (request) {
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
            });
        }
    });
});
