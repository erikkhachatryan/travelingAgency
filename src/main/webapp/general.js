function makeRestCall() {
    $.ajax({
        url:"http://localhost:8080/service",
        method: "GET",
        success: function(response) {
            $("[id$='helloOutputLabelId']").html(response);
        }
    });
}