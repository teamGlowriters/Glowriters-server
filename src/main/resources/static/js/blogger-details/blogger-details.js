// 더보기 버튼을 클릭하면 모달이 생성되고 다시 클릭하면 모달이 사라짐
// 대신 화면의 다른 부분을 클릭해도 모달이 없어져야됨

const modal = document.querySelector("div.layer-action");

document.addEventListener("click", (e) => {
  if (e.target.closest("button.btn-more")) {
    modal.style.display = "block";
  } else {
    modal.style.display = "none";
  }
});

// 차단하기 눌렀을떄 경고창 뜨기
document.addEventListener("DOMContentLoaded", function () {
  var blockButton = document.querySelector(".btn-ctrl-first");

  blockButton.addEventListener("click", function () {
    if (
      confirm(
        "정말로 차단하시겠습니까?\n이 사람이 작성한 댓글은 모두 숨겨지고 이후 내 글에 댓글을 쓰거나 나에게 제안을 할 수 없게 됩니다."
      )
    ) {
      alert("이사람을 차단했습니다!");
    }
  });
});

// // 신고하기 버튼을 눌렀을 때 뜨는 모달
// document.addEventListener("DOMContentLoaded", function () {
//   var reportButton = document.querySelector(".btn-ctrl-second");

//   reportButton.addEventListener("click", function () {
//     var background = document.querySelector(".background");
//     background.style.display = "block";
//   });

//   var reportSubmitButton = document.querySelector(".btn-report");
//   reportSubmitButton.addEventListener("click", function () {
//     alert("신고가 되었습니다.");
//     var background = document.querySelector(".background");
//     background.style.display = "none";
//   });

//   var closeButton = document.querySelector(".btn-close");
//   closeButton.addEventListener("click", function () {
//     var background = document.querySelector(".background");
//     background.style.display = "none";
//   });
// });

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
