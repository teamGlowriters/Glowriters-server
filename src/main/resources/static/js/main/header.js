
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

