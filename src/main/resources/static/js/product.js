
//
// function filterProducts(categoryName) {
//     var products = document.getElementsByClassName('product-item');
//
//     for (var i = 0; i < products.length; i++) {
//         if (categoryName === 'all') {
//             products[i].style.display = 'block';
//         }
//         else {
//             if (products[i].getAttribute('data-category') === categoryName) {
//                 products[i].style.display = 'block';
//             }
//             else {
//                 products[i].style.display = 'none';
//             }
//         }
//     }
// }

// 제일 상단 팝업창
function closePopup() {
    document.getElementById('popup').style.display = 'none';
    document.getElementById('popup').style.height = '0';
    document.getElementById('popup').style.padding = '0';
}
