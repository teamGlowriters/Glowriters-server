//1. 댓글 버튼 클릭시 댓글보임
document.querySelector(".btn-comment").addEventListener("click", function() {
	var infoComment = document.querySelector(".info-comment");
	if (infoComment.style.display === "none") {
		infoComment.style.display = "block";
	} else {
		infoComment.style.display = "none";
	}
});


//2. 댓글 좋아요 버튼 눌렀을때 색깔 바뀌고 좋아요 갯수 카운트
var like_toggle = true;
// 1이면 현재 좋아요 누른 상태 0이면 좋아요 안누른 상태
var isLikeJs = isLike;
console.log(isLikeJs)
const like_btn = document.querySelector(".btn-like")
const like = document.querySelector(".btn-like .like");
const dislike = document.querySelector(".btn-like .dislike");
const like_cnt = document.querySelector(".num-like")
if (isLikeJs == 1) {
	like.style.display = "block";
	dislike.style.display = "none";
} else if (isLikeJs == 0) {
	console.log("0 들어옴");
	like.style.display = "none";
	dislike.style.display = "block";
}
like_btn.addEventListener("click", function() {
	console.log("클릭 됨");
	if (isLikeJs == 1) {
		console.log("1 들어옴");
		like.style.display = "block";
		dislike.style.display = "none";
	} else if (isLikeJs == 0) {
		console.log("0 들어옴");
		like.style.display = "none";
		dislike.style.display = "block";
	}
});



//3. 댓글이 있고 없을때의 뷰 설정
if (isA <= 0) {
	const comment_nothing1 = document.querySelector(".comment-none .ico-brunch.ico-brunch-cheer")
	const comment_nothing2 = document.querySelector(".comment-none .tit-none")
	const comment_nothing3 = document.querySelector(".comment-none .txt-none")
	// 댓글이 존재하는지 
	var comment_view_toggle = false;  //현재는 있다고 가정
	if (comment_view_toggle) {
		comment_nothing1.style.display = "none" //작성된 댓글이 없습니다.와 관련된 모든게 안보임
		comment_nothing2.style.display = "none"
		comment_nothing3.style.display = "none"
	}
	else {
		comment_nothing1.style.display = "block"
		comment_nothing2.style.display = "block"
		comment_nothing3.style.display = "block"
	}
}





// 4. 댓글 설정(...)을 클릭했을 때 작은 신고창 등장
const commentSettings = document.querySelectorAll('.comment-setting');
commentSettings.forEach(function(commentSetting) {
  let commentSettingToggle = false;
  commentSetting.addEventListener("click", function() {
    commentSettingToggle = !commentSettingToggle;
    if (commentSettingToggle) {
      this.classList.add("setting-on");
    } else {
      this.classList.remove("setting-on");
    }
  });

  // 5. 작은 신고창이 있는 상태에서 아무 곳이나 클릭하면 작은 신고창이 사라짐
  document.addEventListener('click', function(event) {
    const isClickInside = event.target.closest('.comment-setting');
    if (!isClickInside) {
      commentSetting.classList.remove('setting-on');
    }
  });

  // 6. 작은 신고창의 신고버튼을 누르면 신고 모달창이 나타남
  const commentSettingButton = commentSetting.querySelector(".btn-set");
  const modal = document.querySelector(".brunch-layer.here");
  commentSettingButton.addEventListener("click", function() {
    modal.style.display = "block";
  });
});

const modalCancel = document.querySelector(".btn-close");
modalCancel.addEventListener("click", function() {
  const modal = document.querySelector(".brunch-layer.here");
  modal.style.display = "none";
});








//8.조은종 코드 신고모달창에서 작동하는것
// 신고 모달에서 체크한 항목에 스타일 적용하기
const radioButtons = document.querySelectorAll(
	'input[type="radio"][name="reasonCode"]'
);

radioButtons.forEach((radioButton) => {
	radioButton.addEventListener("change", function() {
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
document.addEventListener("DOMContentLoaded", function() {
	var reportButton = document.querySelector(".btn-ctrl-second");

	reportButton.addEventListener("click", function() {
		var background = document.querySelector(".background");
		background.style.display = "block";
	});

	var reportSubmitButton = document.querySelector(".btn-report");
	reportSubmitButton.addEventListener("click", function(event) {
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
	closeButton.addEventListener("click", function() {
		var background = document.querySelector(".background");
		background.style.display = "none";
	});

	// textarea를 클릭했을 때 기타 라디오 버튼에 checked 표시를 추가
	var textarea = document.querySelector(".tf_reason");
	textarea.addEventListener("click", function() {
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




