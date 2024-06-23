//카테고리를 선택하는기능
//const category = document.querySelectorAll(".inner-tab li")
//const textInputArea = document.querySelector(".txt-search")
//category.forEach(c => {
//	c.addEventListener("click", () => {
//		//모든 카테고리의 on 클래스제거
//		category.forEach(cate => {
//			cate.classList.remove("on")
//		})
//		//현재 카테고리만 on클래스 생성
//		c.classList.add("on")
//		textInputArea.focus()
//	})
//	console.log("카테고리선택");
//})

//탭을 누르면 글, 블로거 검색으로 바뀜
const checkTabPost = document.querySelector(".inner-tab .list1");
const checkTabBlog = document.querySelector(".inner-tab .list2");

const postTab = document.querySelector(".articleTab.link-tab");
const bloggerTab = document.querySelector(".userTab.link-tab");
const postResult = document.querySelector(".search-result.post");
const bloggerResult = document.querySelector(".search-result.blogger");
function searchTabRemoveOnANDDisplayNone() {
	// on 클래스 모두 제거
	checkTabPost.classList.remove("on");
	checkTabBlog.classList.remove("on");
	// 전부 display: none 처리
	postResult.style.display = "none";
	bloggerResult.style.display = "none";
}

//처음에는 글 검색부터 보여줌
document.addEventListener("DOMContentLoaded", function() {
    searchTabRemoveOnANDDisplayNone();
    checkTabPost.classList.add("on");
    postResult.style.display = "block";  // 글 검색 결과 표시
    console.log("맨처음");
});

postTab.addEventListener("click", function() {
	searchTabRemoveOnANDDisplayNone();
	checkTabPost.classList.add("on");
	postResult.style.display = "block";  // 글 검색 결과 표시
	console.log("postTab");
});

bloggerTab.addEventListener("click", function() {
	searchTabRemoveOnANDDisplayNone();
	checkTabBlog.classList.add("on");
	bloggerResult.style.display = "block";  // 블로거 검색 결과 표시
	console.log("bloggerTab");
});









