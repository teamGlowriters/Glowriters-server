// 취소하기 버튼 누르면 뜨는 모달창
document.addEventListener("DOMContentLoaded", function () {
  const cancelBtn = document.querySelector("button.cancel-btn");
  const modalWrap = document.querySelector("div.cancel-layer");

  cancelBtn.addEventListener("click", (e) => {
    modalWrap.style.display = "block";
  });

  const noBtn = document.querySelector("button.btn");
  const yesBtn = document.querySelector("button.ok-btn");

  noBtn.addEventListener("click", (e) => {
    modalWrap.style.display = "none";
  });

  yesBtn.addEventListener("click", (e) => {
    modalWrap.style.display = "none";
  });
});

// input태그로 넣은 이미지를 미리보기
document.getElementById("imageInput").addEventListener("change", function () {
  var file = this.files[0];
  var reader = new FileReader();

  reader.onload = function (event) {
    document
      .getElementById("previewImage")
      .setAttribute("src", event.target.result);
  };

  reader.readAsDataURL(file);
});

// 작가명 20자 제한
document.addEventListener("DOMContentLoaded", function () {
  var textArea = document.querySelector(".username");
  var maxLength = 20;

  textArea.addEventListener("input", function () {
    var text = this.value;
    if (text.length > maxLength) {
      this.value = text.slice(0, maxLength);
    }
  });
});

// 소개글 200자 제한
document.addEventListener("DOMContentLoaded", function () {
  var textArea = document.querySelector("textarea.introduce-box");
  var maxLength = 200;

  textArea.addEventListener("input", function () {
    var text = this.value;
    if (text.length > maxLength) {
      this.value = text.slice(0, maxLength);
    }
  });
});

// 작가명을 쓰지 않았을 때 뜨는 경고창과 버튼 비활성화
document.addEventListener("DOMContentLoaded", function () {
  var saveButton = document.querySelector(".save-btn");
  var authorNameTextarea = document.querySelector(".username");
  var nameError = document.querySelector(".name-error");

  if (!saveButton || !authorNameTextarea || !nameError) {
    console.error("One or more elements not found!");
    return;
  }

  saveButton.addEventListener("click", validateInputs);

  function validateInputs() {
    var authorNameValue = authorNameTextarea.value.trim();

    if (authorNameValue === "") {
      nameError.style.display = "block";
      return false; // 페이지 전환을 막기 위해 false 반환
    } else {
      nameError.style.display = "none"; // 작가명이 입력되면 p 태그를 숨깁니다.
    }
  }
});

// 소개글을 쓰지 않았을때 뜨는 경고창과 버튼 비활성화
document.addEventListener("DOMContentLoaded", function () {
  var saveButton = document.querySelector(".save-btn");
  var descriptionTextarea = document.querySelector("textarea.introduce-box");
  var introduceError = document.querySelector(".introduce-error");

  if (!saveButton || !descriptionTextarea || !introduceError) {
    console.error("One or more elements not found!");
    return;
  }

  saveButton.addEventListener("click", validateInputs);

  function validateInputs() {
    var descriptionValue = descriptionTextarea.value.trim();

    if (descriptionValue === "") {
      introduceError.style.display = "block";
      return false; // 페이지 전환을 막기 위해 false 반환
    } else {
      introduceError.style.display = "none"; // 소개가 입력되면 p 태그를 숨깁니다.
    }
  }
});
