// 1. 삭제 버튼 누르면 뜨는 모달창
document.addEventListener("DOMContentLoaded", function () {
  const deleteButtons = document.querySelectorAll(".delete-button");
  const modalWrap = document.querySelector(".delete-modal-wrap");

  console.log(deleteButtons);

  deleteButtons.forEach(function (deleteButton) {
    deleteButton.addEventListener("click", (e) => {
      modalWrap.style.display = "flex";
    });
  });
	
	//취소 확인버튼
  const cancelButton = document.querySelector(".modal-cancel button");
  const confirmButton = document.querySelector(".modal-confirm button");
  cancelButton.addEventListener("click", (e) => {
    modalWrap.style.display = "none";
  });

  confirmButton.addEventListener("click", (e) => {
    modalWrap.style.display = "none";
  });
});

// 2. 검색창 눌렀을때 검색바에 아웃라인주기
const searchBar = document.querySelector("label.search-bar");
const mileageInput = document.querySelector("input.content-detail-num");
document.addEventListener("click", (e) => {
  if (e.target.closest("label.search-bar")) {
    searchBar.classList.add("search-bar-checked");
    return;
  }
  searchBar.classList.remove("search-bar-checked");
});

const inputField = document.querySelector(".search-bar input");
const cancelButton = document.querySelector(".search-bar .cancel-logo");
const searchButton = document.querySelector(".search-bar .search-logo");

// 3. 검색어 입력란에 입력할때
function handleInputChange() {
  const inputValue = inputField.value.trim();

  if (inputValue !== "") {
    cancelButton.style.display = "flex";
    searchButton.style.display = "none";
  } else {
    cancelButton.style.display = "none";
    searchButton.style.display = "flex";
  }
}
// cancel-logo를 클릭했을 때 실행될 함수를 정의
function handleCancelClick() {
  inputField.value = "";
  cancelButton.style.display = "none";
  searchButton.style.display = "flex";
}
inputField.addEventListener("input", handleInputChange);
cancelButton.addEventListener("click", handleCancelClick);





// 4. 체크박스 js
const allCheck = document.querySelector(".all-check");
const checkboxes = document.querySelectorAll(".checkbox-input");
// 4-0. 체크박스 체크/해제 시 해당 li 태그에 checked 클래스 추가/제거
function toggleCheckedClass(li, isChecked) {
	if (isChecked) {
		li.classList.add("checked");
	} else {
		li.classList.remove("checked");
	}
}
// 4-1. all-check 체크 여부에 따라 checkbox-input 체크 여부 조절
allCheck.addEventListener("change", function() {
	checkboxes.forEach(function(checkbox) {
		checkbox.checked = allCheck.checked;
		toggleCheckedClass(checkbox.parentNode, allCheck.checked);
	});
});
// 4-2. checkbox-input 중 하나라도 체크가 해제되면 all-check 체크 해제
checkboxes.forEach(function(checkbox) {
	checkbox.addEventListener("change", function() {
		let allChecked = true;
		checkboxes.forEach(function(checkbox) {
			if (!checkbox.checked) {
				allChecked = false;
			}
		});
		allCheck.checked = allChecked;
		toggleCheckedClass(checkbox.parentNode, checkbox.checked);
	});
});
// 4-3. checkbox-input 모두 체크되면 all-check 체크
checkboxes.forEach(function(checkbox) {
	checkbox.addEventListener("change", function() {
		let allChecked = true;
		checkboxes.forEach(function(checkbox) {
			if (!checkbox.checked) {
				allChecked = false;
			}
		});
		allCheck.checked = allChecked;
		toggleCheckedClass(checkbox.parentNode, checkbox.checked);
	});
});

//5. 검색어 입력창에서 엔터가 눌렸을때 비동기통신 시작
function handleEnterKey(event) {
	if (event.keyCode === 13) { // 엔터키 코드는 13
		event.preventDefault(); // 기본 동작 방지 (새로고침 방지)
		const searchInput = document.querySelector(".search-bar.this");
		search(searchInput.value);
	}
}

//6. 체크박스 선택안되는 오류 해결하는 코드
//비동기통신결과로 DOM요소가 변하면 이미 선택자로 선택된 요소들이 오류나서
//비동기통신 할때마다 아래 함수를 호출할때마다 다시 선택하게 함
//체크박스 핸들러 업데이트 함수
function updateCheckboxHandlers() {
	const allCheck = document.querySelector(".all-check");
	const checkboxes = document.querySelectorAll(".checkbox-input");

	//위에서 작성한 로직 그대로 또 씀
	// 6-1. all-check 체크 여부에 따라 checkbox-input 체크 여부 조절
	allCheck.addEventListener("change", function() {
		checkboxes.forEach(function(checkbox) {
			checkbox.checked = allCheck.checked;
			toggleCheckedClass(checkbox.parentNode, allCheck.checked);
		});
	});
	// 6-2. 하나라도 체크가 해제되면 all-check 체크 해제
	checkboxes.forEach(function(checkbox) {
		checkbox.addEventListener("change", function() {
			let allChecked = true;
			checkboxes.forEach(function(checkbox) {
				if (!checkbox.checked) {
					allChecked = false;
				}
			});
			allCheck.checked = allChecked;
			toggleCheckedClass(checkbox.parentNode, checkbox.checked);
		});
	});
}

//7. 실제 검색을 통해 비동기 갱신
function search(text) {
	$.ajax({
		url: "/manager/user/search",
		type: "GET",
		data: { "text": text },
		success: function(frag) {
			$("#userList").replaceWith(frag);
//			console.log("ajax 성공");
//			console.log(frag);
			updateCheckboxHandlers();
		}
	});
}

//8. 삭제기능 : 체크박스 선택하고 삭제버튼 누르고 확인눌렀을때 작동
// modal-confirm 버튼 클릭 이벤트 리스너 추가
const modalConfirmButton = document.querySelector(".modal-confirm button");
modalConfirmButton.addEventListener("click", sendCheckedReportIds);
// 체크정보들
function sendCheckedReportIds() {
	const checkedLis = document.querySelectorAll(".list-content.checked"); //체크 된것들만
	//가져와서 그값을 배열로 묶는다.
	const reportIds = Array.from(checkedLis).map(li => li.getAttribute("report_id"));

	if (reportIds.length === 0) {
		alert("선택된 댓글이 없습니다.");
		return;
	}
	
	//삭제 ajax통신
	//json데이터보내는 형식은 POST로 고정이다!!
	$.ajax({
		url: "/manager/user/delete/delete", // 서버 엔드포인트 URL
		type: "POST",
		contentType: "application/json",
		data: JSON.stringify({ reportIds: reportIds }), //키값은 "reportIds"로 고정인 json데이터 전송
		//서버에서는  @RequestBody Map<String, Object> data 로 매개변수로 받는다.
		success: function(frag) {
			$("#userList").replaceWith(frag);
//			console.log("ajax 성공");
//			console.log(frag);
			updateCheckboxHandlers();
		},
		error: function(xhr, status, error) {
			console.error("실패:", error);
			alert("댓글 삭제 중 오류가 발생했습니다.");
		}
	});
}



