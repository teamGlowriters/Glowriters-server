// const subscribeBtns = document.querySelectorAll(".subscribe-button");
// const subscribeCancelModal = document.querySelector(
//   ".subscriber-realmain-modalWrap"
// );
// const modalBlack = document.querySelector(".modal-black");
// const realBtns = document.querySelectorAll(".continue-button");

// subscribeBtns.forEach((subscribebtn, index) => {
//   subscribebtn.addEventListener("click", () => {
//     console.log("클릭된 거", subscribebtn, index);
//     const clickbtnNumber = index;
//     if (subscribebtn.innerText === "구독하기") {
//       subscribebtn.style.backgroundColor = "#E7BCDE";
//       subscribebtn.style.color = "#fff";
//       subscribebtn.innerText = "구독중";
//     } else {
//       subscribeCancelModal.style.display = "block";
//       modalBlack.style.display = "block";
//       document.body.classList.add("modal-open");
//       realBtns[0].addEventListener("click", (e) => {
//         subscribeCancelModal.style.display = "none";
//         modalBlack.style.display = "none";
//         document.body.classList.remove("modal-open");
//       });
//       realBtns[1].addEventListener("click", (e) => {
//         subscribeCancelModal.style.display = "none";
//         modalBlack.style.display = "none";
//         document.body.classList.remove("modal-open");
//         console.log(clickbtnNumber);
//         subscribeBtns[clickbtnNumber].style.backgroundColor = "#fff";
//         subscribeBtns[clickbtnNumber].style.color = "#E7BCDE";
//         subscribeBtns[clickbtnNumber].innerText = "구독하기";
//       });
//       // realBtns.forEach((realbtn) => {
//       //   realbtn.addEventListener("click", (e) => {
//       //     if (e.target.innerText === "계속 구독하기") {
//       //       subscribeCancelModal.style.display = "none";
//       //       modalBlack.style.display = "none";
//       //       document.body.classList.remove("modal-open");
//       //     } else if (e.target.innerText === "구독 취소하기") {
//       //       subscribeCancelModal.style.display = "none";
//       //       modalBlack.style.display = "none";
//       //       document.body.classList.remove("modal-open");
//       //       console.log(subscribebtn, index);
//       //       subscribebtn.style.backgroundColor = "#fff";
//       //       subscribebtn.style.color = "#E7BCDE";
//       //       subscribebtn.innerText = "구독하기";
//       //     }
//       //   });
//       // });
//     }
//   });
// });

// subscribeBtns.forEach((subscribeBtn, index) => {
//   subscribeBtn.addEventListener("mouseover", (e) => {
//     if (e.target.innerText === "구독중") {
//       e.target.innerText = "구독취소";
//     }
//   });

//   subscribeBtn.addEventListener("mouseout", (e) => {
//     if (e.target.innerText === "구독취소") {
//       e.target.innerText = "구독중";
//     }
//   });
// });
