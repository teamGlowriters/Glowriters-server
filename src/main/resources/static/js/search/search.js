// 로그인 했을 때 안했을 때 
const isLogin = true;

//로그인 상태에따른 제공하는 서비스 제어
const loginservice = document.querySelectorAll(".loginservice.important"); //태그하나씩만 가져옴
const logoutservice = document.querySelectorAll(".logoutservice.important");
const loginservice2 = document.querySelectorAll(".loginservice"); //태그의 자식태그전부다 가져옴
const logoutservice2 = document.querySelectorAll(".logoutservice");
if (isLogin === true) { //로그인 했을때
  loginservice.forEach(e => { e.style.display = 'block'; });
  loginservice2.forEach(e => { e.style.display = 'block'; });
  logoutservice.forEach(e => { e.style.display = 'none'; });
  logoutservice2.forEach(e => { e.style.display = 'none'; });
}
else { //로그인 안했을때
  loginservice.forEach(e => { e.style.display = 'none'; });
  loginservice2.forEach(e => { e.style.display = 'none'; });
  logoutservice.forEach(e => { e.style.display = 'block'; });
  logoutservice2.forEach(e => { e.style.display = 'block'; });
}

//사이드 메뉴바를 눌렀을때 켜지고 꺼지는것
const menuBtn = document.querySelector(".inner-first-btn")
const sideMenu = document.querySelector(".header-sidemenu.open")
const allSide = document.querySelector(".header-sidemenu")
document.addEventListener("click", (e) => {
  
  if (e.target === menuBtn) {
    // console.log("들어옴")
    sideMenu.style.marginLeft = '0px';
  }
  else if (!e.target.closest(".header-sidemenu")) {
    sideMenu.style.marginLeft = '-261px';
  }
})

//여기 까지 헤더 부분


//검색어입력시 placeholder 제거
// const textinput = document.querySelector(".txt-search.this");
// const placeholder = document.querySelector(".txt-search.placeholder")
// textinput.addEventListener("input", () => {
//     placeholder.style.display = "inline";
// })
const searchInput = document.querySelector('.txt-search');
const searchPlaceholder = document.querySelector(".txt-search.placeholder");
const suggestGuide = document.querySelector('.suggestGuide');
const suggestSearch = document.querySelector('.suggest-search');
searchPlaceholder.placeholder = "검색어를 입력해 주세요."; //초기설정
searchPlaceholder.color = "#BFC1C3"

function handleSearchInput() {
  const searchValue = searchInput.value.trim();
  
  if (searchValue.length > 0) {
    searchPlaceholder.placeholder = "ㅤㅤㅤㅤ";
    suggestGuide.style.display = 'none';
    suggestSearch.style.display = 'block';
    // 여기에 검색 결과 가져오는 로직 추가
  } else {
    searchPlaceholder.placeholder = "검색어를 입력해 주세요.";
    suggestGuide.style.display = 'block';
    suggestSearch.style.display = 'none';
  }
}
searchInput.addEventListener('input', handleSearchInput);



// searchInput 요소의 focus 이벤트에 대한 핸들러 추가


searchPlaceholder.addEventListener('focus', function() {
  // 포커스가 들어오면 텍스트 색상을 지정
  input.style.color = 'rgb(251, 251, 251)';
});
// input 요소의 input 이벤트에 대한 핸들러 추가
searchPlaceholder.addEventListener('input', function() {
  // 내용이 입력되면 텍스트 색상을 계속 유지
  input.style.color = 'rgb(251, 251, 251)';
});

// input 요소의 blur 이벤트에 대한 핸들러 추가
searchPlaceholder.addEventListener('blur', function() {
  // 포커스가 빠져나가면 텍스트가 없는 경우 투명한 색상을 유지
  input.style.color = input.value ? 'rgb(251, 251, 251)' : 'transparent';
});



