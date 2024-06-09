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
//여기까지 헤더 부분


const searchInputLayer = document.querySelector('.txt-search');
const searchPlaceholder = document.querySelector(".txt-search.placeholder");
const suggestGuide = document.querySelector('.suggestGuide');
const suggestSearch = document.querySelector('.suggest-search');
searchPlaceholder.placeholder = "검색어를 입력해 주세요."; //초기설정
searchPlaceholder.color = "#BFC1C3"

function handleSearchInput() {
	const searchValue = searchInputLayer.value.trim();

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
searchInputLayer.addEventListener('input', handleSearchInput);



// searchInputLayer 요소의 focus 이벤트에 대한 핸들러 추가
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

//3. 
const searchInput = document.querySelector(".txt-search.this");
const searchPostResults = document.querySelector(".list-article");

searchInput.addEventListener("input", function() {
	var keyword = searchInput.value;

	if (keyword.length > 0) {
		//글 검색 비동기 요청
		$.ajax({
			url: "/search/search/resultPost",
			type: "GET",
			data: { "keyword": keyword },
			success: function(frag) {
				$("#resultPost").replaceWith(frag);
			}
		});

		//블로거 검색 비동기 요청
		$.ajax({
			url: "/search/search/resultBlogger",
			type: "GET",
			data: { "keyword": keyword },
			success: function(frag) {
				$("#resultBlogger").replaceWith(frag);
			}
		});

	} else {

	}
});

$(document).ready(function() {
	$("#searchInput").on()
});


//4. 검색어입력창에서 엔터가 눌렸을때 폼을 제출한다.
function handleEnterKey(event) {
	if (event.keyCode === 13) { // 엔터키 코드는 13
		event.preventDefault(); // 기본 동작 방지 (새로고침 방지)
		document.getElementById("searchForm").submit(); // 폼 제출
	}
}





