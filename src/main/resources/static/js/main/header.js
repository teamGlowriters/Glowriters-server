//로그인 했을 때 안했을 때 설정하는 임시 코드 
const isLogin = true;

//1. 로그인 상태에따른 제공하는 서비스 제어
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


//2. 사이드 메뉴바를 눌렀을때 켜지고 꺼지는것
const menuBtn = document.querySelector(".inner-first-btn")
const sideMenu = document.querySelector(".header-sidemenu.open")
const allSide = document.querySelector(".header-sidemenu")
document.addEventListener("click", (e) => {
	if (e.target === menuBtn) {
		sideMenu.style.marginLeft = '0px';
	}
	else if (!e.target.closest(".header-sidemenu")) {
		sideMenu.style.marginLeft = '-261px';
	}
})


//3.마우스호버이벤트들
const sideMenuLinks = document.querySelectorAll('.header-sidemenu .sidemenu-service li a');
sideMenuLinks.forEach(link => {
	link.addEventListener('mouseenter', () => {
		const span = link.querySelector('span');
		if (span) {
			span.style.borderBottom = '1px solid #E7BCDE';
		}
	});
	link.addEventListener('mouseleave', () => {
		const span = link.querySelector('span');
		if (span) {
			span.style.borderBottom = 'none';
		}
	});
});

//글쓰기나 로그인 버튼이있어서 돋보기가 오른쪽으로 움직이는 기능 안씀
//4. 돋보기 아이콘클릭시 검색바가 부드럽게 생성됨
//const searchInput = document.querySelector('.second-search-input');
//const searchBtn = document.querySelector('.second-search-btn');
//searchBtn.addEventListener('click', () => {
//  searchInput.classList.toggle('active');
//  searchBtn.classList.toggle('active');
//  searchInput.focus(); // 포커스 이동
//});

