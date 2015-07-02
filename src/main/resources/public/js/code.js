$( document ).ready(function() {
    var extendedInstructions = false;

    $( "#create-user" ).submit(function( event ) {
        create_user();
        event.preventDefault();
    });

    var create_user = function() {
        $.ajax({
            url : "/user", // the endpoint
            type : "POST", // http method
            data : { username: $('#bilboname').val(), url: $('#bilbourl').val() },
            success : function(json) {
                $('#userform').hide(); // remove the value from the input
                console.log(json); // log the returned json to the console
                console.log("success"); // another sanity check
            },
            // handle a non-successful response
            error : function(xhr,errmsg,err) {
                alert("Error: team name already taken");
                console.log(xhr.status + ": " + xhr.responseText);
            }
        });
    };

    var update = function() {
        $.getJSON( "/ranking", function( data ) {
            var items = "";
            $.each( data, function( item ) {
                items += "<tr><td>"+(item + 1)+
                         "</td><td>" + data[item].name + "</td>"+
                         "<td>" + data[item].score + "</td>"+
                        "<td>" + data[item].solved + "%</td>"+
                        "<td>" + data[item].length + "</td></tr>";
            });

            $("#ranking-body").html(items);
        });

        if (extendedInstructions !== true) {
            $.getJSON("/instructions", function (data) {
                if (data === "true" || data === true) {
                    $(".extended-instructions").toggle();
                    extendedInstructions = true;
                    console.log("Extended");
                }
            });

        }
    };

    var intervalID = setInterval(function(){update();}, 5000);
});

