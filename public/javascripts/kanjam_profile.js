$(function() {



});

$(".remove-player").click(function(){
    var playerEmail = $(this).parent().parent().parent().find(".player-email").val();
    $("#playerEmail-remove").val(playerEmail);
    $("#remove-player-confirm-msg").html("Are you sure you want to remove " + playerEmail +"?");
});
