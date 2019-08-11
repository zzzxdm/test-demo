// test function
function myFunction() {
    alert("test");
}

$(document).ready(function(){
    $(".submit").click(function() {
        testFormData();
        testJson();
    });

});

// test $.post
var data = {
    name: 'zzz',
    age: 26,
    email: '771668756@qq.com'
}

function testFormData() {
    var url = '/test/testUser';
    $.post(url, data, function (result) {
        var message = JSON.stringify(result);
        console.log(message)
    })
}

// test $.ajax

var postData = JSON.stringify(data)
function testJson() {
    var url = '/test/testUser2';
    $.ajax({
        url : url,
        type: 'POST',
        contentType: "application/json",
        dataType: 'json',
        data: postData,
        success: function (result) {
            var message = JSON.stringify(result);
            console.log(message)
        },
        error: function (exception) {
            console.error(exception)
        }
    })
}