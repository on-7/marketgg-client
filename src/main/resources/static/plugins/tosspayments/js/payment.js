window.addEventListener('DOMContentLoaded', () => {
  const clientKey = 'test_ck_k6bJXmgo28e1RagkzMe8LAnGKWx4';
  const tossPayments = TossPayments(clientKey);

  tossPayments.requestPayment('가상계좌', {
    amount: 1000,
    orderId: 'GGORDER_123',
    orderName: '[생어거스틴] 새우 듬뿍 팟타이 밀키트 외 3건',
    successUrl: 'http://127.0.0.1:5050/payments/success',
    failUrl: 'http://127.0.0.1:5050/payments/fail',
    windowTarget: "iframe",
    customerName: '강태풍',
    customerEmail: 'on7.marketgg@gmail.com',
  });
});
