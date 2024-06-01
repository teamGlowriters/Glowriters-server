//1. 댓글 버튼 클릭시 댓글보임
document.querySelector(".btn-comment").addEventListener("click", function () {
  var infoComment = document.querySelector(".info-comment");
  if (infoComment.style.display === "none") {
    infoComment.style.display = "block";
  } else {
    infoComment.style.display = "none";
  }
});


//2. 댓글 좋아요 버튼 눌렀을때 색깔 바뀌고 좋아요 갯수 카운트
var like_toggle = true;
const like_btn = document.querySelector(".btn-like")
const like = document.querySelector(".btn-like .like");
const dislike = document.querySelector(".btn-like .dislike");
const like_cnt = document.querySelector(".num-like")

like_btn.addEventListener("click", function () {
  like_toggle = !like_toggle; //false <-> true
  var current_like_cnt = like_cnt.textContent;
  if (like_toggle) {
    like.style.display = "block";
    dislike.style.display = "none";
    //좋아요 수 증가  
    like_cnt.textContent = parseInt(current_like_cnt) + 1
  } else {
    like.style.display = "none";
    dislike.style.display = "block";
    //좋아요 수 감소
    like_cnt.textContent = parseInt(current_like_cnt) - 1
  }
});



//3. 댓글이 있고 없을때의 뷰 설정
const comment_nothing1 = document.querySelector(".comment-none .ico-brunch.ico-brunch-cheer")
const comment_nothing2 = document.querySelector(".comment-none .tit-none")
const comment_nothing3 = document.querySelector(".comment-none .txt-none")
// 댓글이 존재하는지 
var comment_view_toggle = false;  //현재는 있다고 가정
if(comment_view_toggle){
  comment_nothing1.style.display="none" //작성된 댓글이 없습니다.와 관련된 모든게 안보임
  comment_nothing2.style.display="none"
  comment_nothing3.style.display="none"
}
else{
  comment_nothing1.style.display="block"
  comment_nothing2.style.display="block"
  comment_nothing3.style.display="block"
}










//4. 댓글 설정( : )눌렀을때 작은 신고창 등장
const comment_setting = document.querySelector(".comment-setting");
var comment_setting_toggle = false;
comment_setting.addEventListener("click", function () {
  comment_setting_toggle = !comment_setting_toggle; //false <-> true
  if (comment_setting_toggle)
    comment_setting.classList.add("setting-on")
  else
    comment_setting.classList.remove("setting-on")
})

//5. 작은 신고창이 있는상태에서 아무대나 클릭하면 작은신고창이 사라짐
document.addEventListener('click', function (event) {
  var isClickInside = event.target.closest('.comment-setting');

  if (!isClickInside) {
    var commentSettingElements = document.querySelectorAll('.comment-setting');
    commentSettingElements.forEach(function (element) {
      element.classList.remove('setting-on');
    });
  }
});


//6. 작은 신고창의 신고버튼을 누르면 신고 모달창이 나타남
const comment_setting_dec = document.querySelector(".btn-set")
const modal = document.querySelector(".brunch-layer.here");
comment_setting_dec.addEventListener("click", function() {
  modal.style.display="block";  
})
const modal_cancel = document.querySelector(".btn-close");
modal_cancel.addEventListener("click", function() {
  modal.style.display="none";  
})

//8.조은종 코드 신고모달창에서 작동하는것
// 신고 모달에서 체크한 항목에 스타일 적용하기
const radioButtons = document.querySelectorAll(
  'input[type="radio"][name="reasonCode"]'
);

radioButtons.forEach((radioButton) => {
  radioButton.addEventListener("change", function () {
    const selectedValue = this.value;

    document.querySelectorAll(".lab-comm, .radio-input").forEach((element) => {
      element.classList.remove("checked");
    });

    const relatedLabel = document.querySelector(`label[for="${this.id}"]`);
    const relatedDiv = relatedLabel.previousElementSibling;
    relatedLabel.classList.add("checked");
    relatedDiv.classList.add("checked");
  });
});

// 신고 모달창에서 기타사유를 입력하지않고 신고를 눌렀을떄 경고창이 뜸
document.addEventListener("DOMContentLoaded", function () {
  var reportButton = document.querySelector(".btn-ctrl-second");

  reportButton.addEventListener("click", function () {
    var background = document.querySelector(".background");
    background.style.display = "block";
  });

  var reportSubmitButton = document.querySelector(".btn-report");
  reportSubmitButton.addEventListener("click", function (event) {
    var radioButtons = document.querySelectorAll(
      'input[type="radio"][name="reasonCode"]'
    );
    var selectedReason = false;
    var otherReasonTextarea = document.querySelector(".tf_reason");
    var otherReasonText = otherReasonTextarea.value.trim();

    radioButtons.forEach((radioButton) => {
      if (radioButton.checked) {
        selectedReason = true;
      }
    });

    if (!selectedReason) {
      alert("신고 이유를 선택해주세요.");
      event.preventDefault();
    } else if (
      document.querySelector("#check7").checked &&
      otherReasonText === ""
    ) {
      alert("기타 사유를 입력해주세요.");
      event.preventDefault();
    } else {
      alert("신고가 되었습니다.");
      var background = document.querySelector(".background");
      background.style.display = "none";
    }
  });

  var closeButton = document.querySelector(".btn-close");
  closeButton.addEventListener("click", function () {
    var background = document.querySelector(".background");
    background.style.display = "none";
  });

  // textarea를 클릭했을 때 기타 라디오 버튼에 checked 표시를 추가
  var textarea = document.querySelector(".tf_reason");
  textarea.addEventListener("click", function () {
    var otherRadio = document.querySelector("#check7");
    otherRadio.checked = true;
  });
});

//
const followBtn = document.querySelector(".follow-button");
const followBtnSpan = document.querySelector(
  ".follow-button > span.follow-button"
);
const subscribeCancelModal = document.querySelector(
  ".subscriber-realmain-modalWrap"
);
const modalBlack = document.querySelector(".modal-black");
const realBtns = document.querySelectorAll(".continue-button");

followBtn.addEventListener("click", () => {
  if (followBtnSpan.innerText == "구독하기") {
    followBtn.style.backgroundColor = "#E7BCDE";
    followBtnSpan.style.color = "#fff";
    followBtnSpan.innerText = "구독중";
  } else {
    subscribeCancelModal.style.display = "block";
    modalBlack.style.display = "block";
    document.body.classList.add("modal-open");
    realBtns.forEach((realbtn) => {
      realbtn.addEventListener("click", (e) => {
        if (e.target.innerText === "계속 구독하기") {
          subscribeCancelModal.style.display = "none";
          modalBlack.style.display = "none";
          document.body.classList.remove("modal-open");
        } else if (e.target.innerText === "구독 취소하기") {
          subscribeCancelModal.style.display = "none";
          modalBlack.style.display = "none";
          document.body.classList.remove("modal-open");
          followBtn.style.backgroundColor = "#fff";
          followBtnSpan.style.color = "#E7BCDE";
          followBtnSpan.innerText = "구독하기";
        }
      });
    });
  }
});

followBtn.addEventListener("mouseover", (e) => {
  if (followBtnSpan.innerText === "구독중") {
    followBtnSpan.innerText = "구독취소";
  }
});

followBtn.addEventListener("mouseout", (e) => {
  if (followBtnSpan.innerText === "구독취소") {
    followBtnSpan.innerText = "구독중";
  }
});
// 조은종코드끝



