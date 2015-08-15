$(function() {



});

$(".remove-player").click(function(){
    $("#playerId").val($(this).closest(".player-email").val());
    alert($(this).parent().parent().parent().siblings("input[name='player-email']").val());
});
