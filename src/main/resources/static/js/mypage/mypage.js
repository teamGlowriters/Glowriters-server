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

document.addEventListener("DOMContentLoaded", function () {
  // 이미지를 클릭했을 때의 동작
  document
    .querySelector(".profile-img img.profile-img")
    .addEventListener("click", function () {
      // display가 none이 아닐 때만 실행
      if (document.querySelector(".big-img").style.display !== "block") {
        // 클릭한 이미지의 정보 가져오기
        var imgInfo = this.getBoundingClientRect();
        var imgSrc = this.getAttribute("src");

        // 큰 화면의 이미지 업데이트
        var bigImg = document.querySelector(".viewer-img img.viewer-img");
        bigImg.setAttribute("src", imgSrc);
        bigImg.style.top = imgInfo.top + "px";
        bigImg.style.left = imgInfo.left + "px";
        bigImg.style.width = imgInfo.width + "px";
        bigImg.style.height = imgInfo.height + "px";

        // 큰 화면 보이기
        var bigImgContainer = document.querySelector(".big-img");
        bigImgContainer.style.display = "block";

        // 천천히 나타나도록 설정
        setTimeout(function () {
          bigImg.style.transition = "all 0.5s";
          bigImg.style.top = "50%";
          bigImg.style.left = "50%";
          bigImg.style.width = "500px";
          bigImg.style.height = "500px";
        }, 100);
      }
    });

  // 큰 화면을 클릭했을 때의 동작
  document.querySelector(".big-img").addEventListener("click", function () {
    // display가 block일 때만 실행
    if (this.style.display === "block") {
      // 큰 화면 사라지도록 설정
      var bigImg = document.querySelector(".viewer-img img.viewer-img");
      bigImg.style.transition = "all 0.5s";
      bigImg.style.top = "auto";
      bigImg.style.left = "auto";
      bigImg.style.width = "100px";
      bigImg.style.height = "100px";

      // 천천히 사라지도록 설정
      setTimeout(function () {
        var bigImgContainer = document.querySelector(".big-img");
        bigImgContainer.style.display = "none";
      }, 500);
    }
  });
});
