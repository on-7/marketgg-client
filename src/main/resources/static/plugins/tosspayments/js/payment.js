const clientKey = 'test_ck_k6bJXmgo28e1RagkzMe8LAnGKWx4';
const tossPayments = TossPayments(clientKey);

// 결제하기 버튼
const button = document.getElementById('payment-button');
button.addEventListener('click', function () {
  tossPayments.requestPayment('카드', {
    amount: 1,
    orderId: 'DIiACXKp69bRQUvCZgL91',
    orderName: '[KF365] 아보카도 200g (1걔) 외 2건',
    successUrl: 'http://127.0.0.1:5050/payments/success',
    failUrl: 'http://127.0.0.1:5050/payments/fail',
    windowTarget: "iframe",
    customerName: '강태풍',
    customerEmail: 'on7.marketgg@gmail.com',
  });
});
