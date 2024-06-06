//1. 사이드 메뉴바를 눌렀을때 켜지고 꺼지는것
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
//여기 까지 헤더

//2. 메인 슬라이드의 다음/이전 버튼
const nextbtn = document.querySelector(".slide-next")
const prevbtn = document.querySelector(".slide-prev")
const slideUl = document.querySelector(".slide-area")
//총 8개 보여줄때의 xdegree값들이다.
var xdegree = 0;
nextbtn.addEventListener("click", () => {
	xdegree -= 500;
	if (xdegree === -500) { prevbtn.style.display = "block"; }
	with (slideUl.style) {
		transform = `translateX(${xdegree}px)`;
		transition = "transform 0.3s ease 0s";
	}
	xdegree === -3000 ? (nextbtn.style.display = "none") : (nextbtn.style.display = "block");
});

prevbtn.addEventListener("click", () => {
	xdegree += 500;
	if (xdegree === 0) { prevbtn.style.display = "none"; };
	with (slideUl.style) {
		transform = `translateX(${xdegree}px)`;
		transition = "transform 0.3s ease 0s";
	}
	xdegree === -2500 && (nextbtn.style.display = "block");
});

//3. 스크롤 내리다가 추천작가를 만나면 top버튼 활성화
const topBtn = document.querySelector('.goto-top');
const writersSection = document.querySelector('.writers');
const footer = document.querySelector('.footer');

document.addEventListener('DOMContentLoaded', function() {
	function scrollToTop() {
		window.scrollTo({ top: 0, behavior: 'auto' }); //smooth를 주면 스크롤되며 이동
	}

	function handleScroll() {
		const writersTop = writersSection.getBoundingClientRect().top + window.scrollY;
		const footerTop = footer.getBoundingClientRect().top + window.scrollY;

		if (window.scrollY > writersTop && window.scrollY < footerTop - window.innerHeight) {
			topBtn.classList.add('on');
			topBtn.addEventListener('click', scrollToTop);
			topBtn.style.bottom = ""; //원래 대로
		} else {
			topBtn.classList.remove('on');
			topBtn.removeEventListener('click', scrollToTop);
			topBtn.style.bottom = "20px"; //0으로 지정
		}
	}

	document.addEventListener('scroll', handleScroll);
});


//4. fslide - 추천작가 슬라이드의 버튼
//나중에
const fnextbtn = document.querySelector(".fslide-next")
const fprevbtn = document.querySelector(".fslide-prev")
const fslideUl = document.querySelector(".fslide")
const fslideArea = document.querySelector(".fslide-wrap")
//var slideWidth = window.getComputedStyle(fslideArea).getPropertyValue('width');
//var contentCnt = slideWidth / 300;
//console.log("slideWidth / 300 => " + slideWidth / 300);

//var contentCnt = [[${footerSlideItemsCount}]];
console.log("contentCnt => " + contentCnt);

var index = 0;
var xdegree = 0;
const slideContentCnt = 3;  // 슬라이드 한번 넘길때 3개의 게시물이 지나감
var deg = slideContentCnt * 300; // 결론 한번에 넘어가는 x축각

//버튼이 보이는지 안보이는지 컨트롤
function btnController(){
	console.log("index = " + index);
	console.log("xdegree = " + xdegree);
	if (index == 0) //처음 위치로 왔을때
		fprevbtn.style.display = "none"; //이전버튼 안보임
	else	//한번이라도 오른쪽으로 넘겼을때
		fprevbtn.style.display = "block"; //이전버튼 보임
	//보여줄 수 있는 게시물을 전부다 보여주면
	if (index >= contentCnt - slideContentCnt)
		fnextbtn.style.display = "none";
	else	//아직 보여지지않은 게시물이 남으면
		fnextbtn.style.display = "block";
}

// DB에서 게시물을 다 가져왔으면 next버튼을 지우도록 수정해야함
fnextbtn.addEventListener("click", () => {
	index += slideContentCnt;
	xdegree -= deg;

	with (fslideUl.style) {
		transform = `translateX(${xdegree}px)`; // 결론 한번에 넘어가는 x축각 962
		transition = "transform 0.3s ease 0s";
	}
	btnController();
});

fprevbtn.addEventListener("click", () => {
	index -= slideContentCnt;
	xdegree += deg;

	with (fslideUl.style) {
		transform = `translateX(${xdegree}px)`;
		transition = "transform 0.3s ease 0s";
	}
	btnController();
});



//5. 로그인창 on off
const modalOn = document.querySelector(".inner-third-login.logoutservice")
const modalOff = document.querySelector(".ico-close")
const modal = document.querySelector(".layer-login")
modalOn.addEventListener("click", () => {
	modal.style.display = "block";
})
modalOff.addEventListener("click", () => {
	modal.style.display = "none";
})


//6. 로그인창 왼쪽 슬라이드
const slides = document.querySelectorAll(".slide-item")
const slidesdots = document.querySelectorAll(".btn-paging")
var slideIdx = 0;
//슬라이드의 한장씩 보여주도록
function slideshow(idx) {
	slides.forEach((slide, i) => {
		if (i === idx)
			slide.style.display = "block"; //한장만 보이고
		else
			slide.style.display = "none"; //나머지는 블락
	});
	slidesdots.forEach((dot, i) => {
		if (i === idx)
			dot.classList.add("on"); //on:after 일때 보임
		else
			dot.classList.remove("on"); //나머지는 안보이게
	});
}
//한장 앞으로 넘기는 동작
function nextslide() {
	slideIdx = (slideIdx + 1) % 3
	slideshow(slideIdx)
}

slideshow(slideIdx) //맨처음 동작

setInterval(nextslide, 3000)


//7. 로그인창 슬라이드의 버튼을 누를떄 움직임
const lprevbtn = document.querySelector(".btn-prev-ico")
const lnextbtn = document.querySelector(".btn-next-ico")
//한장 뒤로 넘기는 동작
function prevslide() {
	slideIdx = (slideIdx + 2) % 3
	slideshow(slideIdx)
}
lprevbtn.addEventListener("click", () => {
	prevslide()
})
lnextbtn.addEventListener("click", () => {
	nextslide()
})

//8. 요일별 연재의 요일탭을 눌렀을때 
const weeksBtns = document.querySelectorAll(".link-tab");
weeksBtns.forEach(w => {
	w.addEventListener("click", (currentBtn) => {
		weeksBtns.forEach(btn => {
			btn.style.color = "#959595"; //다른 모든 요일의 색상을 리셋하고
			btn.classList.remove("on"); //클래스도 리셋
		});

		currentBtn.target.style.color = "#111"; //현재 요일에만 색상적용
		currentBtn.target.classList.add("on"); //클래스도 추가
	})
});

//9. 요일별 탭눌렀을때 호출되는 함수. 요일을 기록하는 용도
var weekend = "Monday"; //기본값
function selectWeek(data) {
	weekend = data;
}

//10. 요일별 탭을 눌렀을때 ajax요청 전송
//ajax를 쓰면 비동기(멀티쓰레드식)로 컨트롤러가 작동하여 필요한 부분만 현재 페이지로 제공
function dataSend() {
	console.log(link + "dataSend호출중");
	$.ajax({
		url: link, //기록 한 url로 요청을전송
		type: "GET",
		data: { week: weekend }, //기록한 요일로 요청에 데이터까지 전송
		cache: false,
		async: false,
	}).done(function(frag) { //비동기로가져온 데이터를 화면에 그린다. 여기서는 요일별게시글의 타임리프 뷰
		$("#weekends").replaceWith(frag); //그리고 싶은곳
		//		console.log("성공");
		//		console.log(frag);

	}).fail(function(jqXHR) {
		//		console.log("실패");
		//		console.log(jqXHR);

	});
}

//11. 요일별 연재의 정렬순서를 눌렀을때
const recentBtns = document.querySelectorAll(".filter-btn");
var weekStatus;
var link = "weekends/recent";
recentBtns.forEach(list => {
	list.addEventListener("click", (e) => {
		recentBtns.forEach(btn => { //먼저 모든 on클래스를 제거
			btn.classList.remove("on");
		})
		e.target.classList.add("on"); //그리고 현재 클릭한 요소에 on 클래스 추가

		weekStatus = e.target.textContent.trim();
		if (weekStatus === "최신순")
			link = "weekends/recent";
		else if (weekStatus === "라이킷순")
			link = "weekends/like";

		console.log(link + "dataSend호출전");
		dataSend(); //정렬 순서를 누를때도 ajax요청을 보냄
	})
})







