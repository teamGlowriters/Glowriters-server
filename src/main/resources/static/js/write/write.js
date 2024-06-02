const upperfile = document.querySelector(".photo-file-input");
const upperInputFile = document.querySelector(".upperPhoto");
const removeIcon = document.querySelector(".real-remove-icon");

upperfile.addEventListener("change", function () {
  const file = this.files[0];
  const reader = new FileReader();

  reader.onload = function (e) {
    upperInputFile.src = e.target.result;
  };

  reader.readAsDataURL(file);
  removeIcon.style.display = "inline-block";
});

removeIcon.addEventListener("click", function () {
  upperInputFile.src = "";
  removeIcon.style.display = "none";
});



// // 이미지첨부 버튼
// const lowerfile = document.querySelector(".side-file-input");
// // 실제 내용이 쌓이는 본문위치. 여기에 태그가 생김
// const contentDiv = document.querySelector(".content-wrap-div");

// // 페이지 로딩 시 보이지 않는 이미지 첨부
// // 이렇게 해야 처음부터끝까지 모든 입력내용이 content-item이라는 클래스의 div태그안에 담김
// window.addEventListener('load', function () {
//   const hiddenImageURL = 'data:image/gif;base64,R0lGODlhAQABAIAAAAUEBA...'; //아무거나

//   const divTag = document.createElement("div");
//   divTag.classList.add("content-item");

//   const img = document.createElement("img");
//   img.src = hiddenImageURL;
//   img.style.width = "1px"; 
//   img.style.height = "1px";

//   divTag.appendChild(img);
//   contentDiv.appendChild(divTag);
// });

// // content-wrap-div 요소에 대한 이벤트 리스너 추가
// contentDiv.addEventListener("keydown", function (event) {
//   // 사용자가 Enter 키를 눌렀을 때
//   if (event.key === 13) {
//     event.preventDefault(); // 새로운 줄 생성 방지
//     const text = contentDiv.innerText.trim(); 

//     if (text !== "") {
//       const divTag = document.createElement("div");
//       divTag.classList.add("content-item");

//       const pTag = document.createElement("p");
//       pTag.innerText = text;

//       divTag.appendChild(pTag);

//       contentDiv.appendChild(divTag);

//       contentDiv.innerText = "";
//     }
//   }
// });

// // lowerfile의 change 이벤트에 대한 리스너 추가
// lowerfile.addEventListener("change", function () {
//   const file = this.files[0]; // 선택된 파일 가져오기
//   const reader = new FileReader(); // 파일 리더 객체 생성

//   reader.onload = function (e) {
//     // 새로운 div 태그 생성
//     const divTag = document.createElement("div");
//     divTag.classList.add("content-item");

//     // 이미지를 새로운 img 요소로 생성하여 divTag에 삽입
//     const img = document.createElement("img");
//     img.src = e.target.result;
//     img.style.width = "360px";
//     img.style.height = "360px";

//     // divTag에 img 태그 삽입
//     divTag.appendChild(img);

//     // contentDiv에 새로운 div 태그 삽입
//     contentDiv.appendChild(divTag);
//   };

//   // 선택된 파일을 읽기
//   reader.readAsDataURL(file);
// });


// 카테고리
const categorybtn = document.querySelector(".category-btn");
const categoryModal = document.querySelector(".declaration-modal-wrap");
const enterBtn = document.querySelector(".declaration-btn-container");

categorybtn.addEventListener("click", () => {
  categoryModal.style.display = "flex";
});

enterBtn.addEventListener("click", () => {
  categoryModal.style.display = "none";
});

const declarationLabels = document.querySelectorAll(".declaration-label");
const declarationInputs = document.querySelectorAll(".declaration-input");
declarationLabels.forEach((item) => {
  item.addEventListener("click", () => {
    declarationInputs.forEach((radio, i) => {
      if (radio.checked) {
        radio.parentNode.classList.add("declaration-choice");
      } else {
        radio.parentNode.classList.remove("declaration-choice");
      }
    });
  });
});

// 본문에 이미지 첨부
const imageUploadBoxes = document.querySelectorAll('.image-upload-box');

imageUploadBoxes.forEach((box) => {
  box.addEventListener('click', () => {
    // 이미지가 이미 존재하는지 확인
    const existingImage = box.querySelector('img');
    if (existingImage) {
      // 이미지가 이미 존재하면 기존 이미지 제거
      box.removeChild(existingImage);
    } else {
      // 이미지가 비어있을 때만 파일 업로드 처리
      const fileInput = document.createElement('input');
      fileInput.type = 'file';
      fileInput.accept = 'image/*';
      fileInput.addEventListener('change', (event) => {
        const file = event.target.files[0];
        const reader = new FileReader();
        reader.onload = () => {
          const img = document.createElement('img');
          img.onload = () => {
            const maxWidth = 220;
            const maxHeight = 220;
            let width = img.width;
            let height = img.height;

            if (width > height) {
              if (width > maxWidth) {
                height *= maxWidth / width;
                width = maxWidth;
              }
            } else {
              if (height > maxHeight) {
                width *= maxHeight / height;
                height = maxHeight;
              }
            }

            img.style.width = width + 'px';
            img.style.height = height + 'px';
            img.style.objectFit = 'contain';
          };
          img.src = reader.result;
          img.style.maxWidth = '100%';
          box.innerHTML = ''; // 이미지를 업로드할 박스 내용 비우기
          box.appendChild(img); // 이미지 업로드 박스에 이미지 추가
        };
        reader.readAsDataURL(file);
      });
      fileInput.click();
    }
  });
});



// textarea내부에 스크롤바가 생기는대신  textarea전체 높이를 증가시키는코드
// textarea의 내용이 변경될 때마다 높이를 조절하는 함수
function adjustTextareaHeight(textarea) {
  textarea.style.height = 'auto'; // 높이를 auto로 설정하여 기존 높이를 초기화

  // 현재 textarea의 스크롤 가능한 높이를 가져옴
  var scrollHeight = textarea.scrollHeight;

  // textarea의 높이를 스크롤 가능한 높이로 설정
  textarea.style.height = scrollHeight + 'px';
}

// textarea 요소들에 대해 이벤트 리스너를 추가
document.querySelectorAll('textarea').forEach(function(textarea) {
  // textarea의 내용이 변경될 때마다 높이를 조절하는 이벤트 리스너 추가
  textarea.addEventListener('input', function() {
    adjustTextareaHeight(this); // 현재 textarea의 높이를 조절
  });
  
  // 페이지 로드 후 textarea의 초기 높이를 조절
  adjustTextareaHeight(textarea);
});


