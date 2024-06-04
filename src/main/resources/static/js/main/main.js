
//2. 사이드 메뉴바를 눌렀을때 켜지고 꺼지는것
const menuBtn = document.querySelector(".inner-first-btn")
const sideMenu = document.querySelector(".header-sidemenu.open")
const allSide = document.querySelector(".header-sidemenu")
document.addEventListener("click", (e)=>{
    if(e.target === menuBtn){
        sideMenu.style.marginLeft = '0px';
    }
    else if(!e.target.closest(".header-sidemenu")){
        sideMenu.style.marginLeft = '-261px';
    }
})

//여기 까지 헤더 부분






//3. 메인 슬라이드의 다음/이전 버튼
const nextbtn = document.querySelector(".slide-next")
const prevbtn = document.querySelector(".slide-prev")
const slideUl = document.querySelector(".slide-area")
//총 8개 보여줄때의 xdegree값들이다.
var xdegree = 0;
nextbtn.addEventListener("click",()=>{
    xdegree -= 500;
    if(xdegree === -500){ prevbtn.style.display = "block"; } 
    with (slideUl.style) {
        transform = `translateX(${xdegree}px)`;
        transition = "transform 0.3s ease 0s";
    }
    xdegree === -3000 ? (nextbtn.style.display = "none") : (nextbtn.style.display = "block");
});

prevbtn.addEventListener("click", () => {
    xdegree += 500;
    if(xdegree === 0){ prevbtn.style.display = "none"; };
    with (slideUl.style) {
        transform = `translateX(${xdegree}px)`;
        transition = "transform 0.3s ease 0s";
    }
    xdegree === -2500 && (nextbtn.style.display = "block");
});


//4. 요일별 연재의 요일을 눌렀을때 
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


//5. 요일별 연재의 정렬순서를 눌렀을때
const recentBtns = document.querySelectorAll(".filter-btn");
recentBtns.forEach(list => {
    list.addEventListener("click", (e) => {
        recentBtns.forEach(btn => { //최신순, 라이킷순에 붙은 on 클래스 전부 해제
            btn.classList.remove("on");
        })
        e.target.classList.add("on"); //그리고 현재 클릭한 정렬요소에 on 클래스 추가
    })
})


//6. 스크롤 내리다가 추천작가를 만나면 top버튼 활성화
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


//7. fslide - 추천작가 슬라이드의 버튼
//나중에
const fnextbtn = document.querySelector(".fslide-next")
const fprevbtn = document.querySelector(".fslide-prev")
const fslideUl = document.querySelector(".fslide")
var xdegree = 0;

var contentCnt = 6;  // 슬라이드 한번 넘길때 3.7개의 게시물이 지나감
const slideContentCnt = 3.7;  // 게시물 하나의 크기 240px + 패딩20px
var deg = slideContentCnt * 260; // 결론 한번에 넘어가는 x축각 962
// DB에서 게시물을 다 가져왔으면 next버튼을 지우도록 수정해야함
fnextbtn.addEventListener("click",()=>{
    xdegree -= deg; 
    
    with (fslideUl.style) {
        transform = `translateX(${xdegree}px)`;
        transition = "transform 0.3s ease 0s";
    }
    if (xdegree === -deg ) fprevbtn.style.display = "block"; //이전버튼 보임
     // 마지막 화면이 되면 next 버튼을 숨김
    if (-(contentCnt - 2) * 260 >= xdegree ) {
        fnextbtn.style.display = "none";
    }
    console.log(-(contentCnt - 1) * 260);
    console.log(xdegree);
});

fprevbtn.addEventListener("click", () => {
    xdegree += deg;
    console.log(xdegree);
    with (fslideUl.style) {
        transform = `translateX(${xdegree}px)`;
        transition = "transform 0.3s ease 0s";
    }
    // 첫화면으로 돌아오면
    if (xdegree === 0) {
        fprevbtn.style.display = "none"; //이전버튼 숨김
        fnextbtn.style.display = "block"; //다음버튼 보임
    }
    
});


//8. 로그인창 on off
const modalOn = document.querySelector(".inner-third-login.logoutservice")
const modalOff = document.querySelector(".ico-close")
const modal = document.querySelector(".layer-login")
modalOn.addEventListener("click", () => {
    modal.style.display = "block";
})
modalOff.addEventListener("click", () => {
    modal.style.display = "none";
})


//9. 로그인창 왼쪽 슬라이드
const slides = document.querySelectorAll(".slide-item")
const slidesdots = document.querySelectorAll(".btn-paging")
var slideIdx = 0;
//슬라이드의 한장씩 보여주도록
function slideshow(idx){
    slides.forEach((slide, i) => {
        if(i === idx)
            slide.style.display = "block"; //한장만 보이고
        else
            slide.style.display = "none"; //나머지는 블락
    });
    slidesdots.forEach((dot, i) => {
        if(i === idx)
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


//11. 로그인창 슬라이드의 버튼을 누를떄 움직임
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











