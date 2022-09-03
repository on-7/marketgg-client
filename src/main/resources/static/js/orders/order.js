window.addEventListener('DOMContentLoaded', () => {
  const $decidedCoupon = document.getElementById('selected-coupon')

  /**
   * 쿠폰 사용 시 input 태그에 히든 값으로 쿠폰 적용 금액을 설정합니다.
   */
  const calculatePrice = () => {
    const $ul = document.getElementById('selected-coupons');
    $ul.addEventListener('click', () => {
      $ul.childNodes.forEach((item, i) => {
        if (item.classList.contains("active")) {
          const $selectedCoupon = document.getElementById('coupons').children[0].children[i];
          const $selectedCouponName = document.getElementById('selected-coupon-name');

          $decidedCoupon.value = $selectedCoupon.value;

          // FIXME: 확인 후 삭제 요망
          console.log($decidedCoupon);
          $selectedCouponName.value = item.textContent;
          return false;
        }
      });
    });
  };

  calculatePrice();

  /**
   * 적립금 적용 입력 시 이벤트를 설정합니다.
   * @type {HTMLElement}
   */
  const $usedPointInput = document.getElementById('used-point');
  $usedPointInput.addEventListener('input', (event) => {
    $usedPointInput.value = event.target.value;
    console.log($usedPointInput.value);
  });

  /**
   * 결제하기 버튼 클릭 시 쿠폰 적용 및 적립금 금액과 최종 결제 금액 유효성 검증 후 결제를 진행합니다.
   * @type {HTMLElement}
   */
  const $paymentButton = document.getElementById('order-to-payment-button')
  $paymentButton.addEventListener('click', (event) => {
    // 기존 form 태그 submit 이벤트를 막기 위함
    event.preventDefault();

    const $usedPoint = document.getElementById('used-point');
    const $totalAmount = document.getElementById('total-origin');

    if (!isValidPaymentAmount(parseInt($totalAmount.value),
                              parseInt($decidedCoupon.value), parseInt($usedPoint.value))) {
      alert("결제 금액보다 초과하여 할인을 적용할 수 없습니다.");
      return false;
    }

    // FIXME: 확인 후 삭제 요망
    console.log($usedPoint.value);
    console.log($decidedCoupon.value);
    console.log($usedPoint.value);
    console.log($totalAmount.value);

    // 총 금액에서 할인 계산
    $totalAmount.value = parseInt($totalAmount.value) - (parseInt($decidedCoupon.value) + parseInt($usedPoint.value));

    document.getElementById('order-form')
            .submit();
  });

  const isValidPaymentAmount = (totalAmount, couponDiscountAmount, usedPoint) => {
    let fixedAmount = isFixedRate(couponDiscountAmount) ? couponDiscountAmount * 100 : couponDiscountAmount;
    // FIXME: 확인 후 삭제 요망
    console.log(`totalAmount: ${typeof totalAmount}`);
    console.log(`fixedAmount: ${typeof fixedAmount}`);
    console.log(`usedPoint: ${typeof usedPoint}`);
    console.log(totalAmount < fixedAmount + usedPoint);

    return totalAmount >= fixedAmount + usedPoint;
  };

  /**
   * 쿠폰 할인 적용 금액이 정률인지 검사합니다.
   * @param couponDiscountAmount - 쿠폰 할인 금액
   * @returns {boolean} - 쿠폰 적용 금액이 정률이면 true, 정액이면 false
   */
  const isFixedRate = (couponDiscountAmount) => {
    return couponDiscountAmount % 1 !== 0;
  }
});
