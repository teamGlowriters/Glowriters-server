//알림 삭제 버튼 누르면 비동기통신 이후 알림 갱신
const btns = document.querySelectorAll(".delete-alarm");
btns.forEach(function(btn) {
    btn.addEventListener("click", function() {
        var alarm_id = this.getAttribute("alarm_id"); //태그에 저장된 알림 아이디 가져옴
        console.log("가져온 알림아이디 = " + alarm_id);
        
        //삭제를 진행하고 새로운 알림페이지를 비동기 갱신하도록 컨트롤러 호출
        $.ajax({
            url: "/alarm/alarm/delete",
            type: "GET",
            data: {"alarm_id" : alarm_id},
            success: function(frag) {
                console.log(frag);
                $("#notice").replaceWith(frag); //새로만들어진 알림으로 갱신
            }
        });
        
    });
});

