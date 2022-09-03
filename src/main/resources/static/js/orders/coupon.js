window.onload = function () {
  createSelect();
}

const DATA_INDEX = 'data-index';
const DATA_INDEX_SELECT = 'data-indx-select';
const DATA_N_SELECT = 'data-n-select';

const createSelect = () => {
  const li = [];
  const div_cont_select = document.querySelectorAll('[data-mate-select="active"]');

  let select_ = '';
  for (let e = 0; e < div_cont_select.length; e++) {
    div_cont_select[e].setAttribute(DATA_INDEX_SELECT, e);
    div_cont_select[e].setAttribute('data-selec-open', 'false');

    select_ = document.querySelectorAll("[" + DATA_INDEX_SELECT + "='" + e + "'] >select")[0];
    if (isMobileDevice()) {
      select_.addEventListener('change', function () {
        _selectOption(select_.selectedIndex, e);
      });
    }

    const selectOptions = select_.options;
    document.querySelectorAll("[" + DATA_INDEX_SELECT + "='" + e + "']  > .selected-option ")[0]
      .setAttribute(DATA_N_SELECT, e);

    for (let i = 0; i < selectOptions.length; i++) {
      li[i] = document.createElement('li');
      if (selectOptions[i].selected === true || select_.value === selectOptions[i].innerHTML) {
        li[i].className = 'active';
        document.querySelector(
          "[" + DATA_INDEX_SELECT + "='" + e + "']  > .selected-option ").innerHTML = selectOptions[i].innerHTML;
      }

      li[i].setAttribute(DATA_INDEX, i);
      li[i].setAttribute(DATA_INDEX_SELECT, e);

      // function click all selected
      li[i].addEventListener('click', function () {
        _selectOption(this.getAttribute(DATA_INDEX), this.getAttribute(DATA_INDEX_SELECT));
      });

      li[i].innerHTML = selectOptions[i].innerHTML;

      const ul_cont = document.querySelectorAll(
        "[" + DATA_INDEX_SELECT + "='" + e + "'] > .cont_list_select_mate > ul");
      ul_cont[0].appendChild(li[i]);

    } // Fin For selectOptions
  } // fin for divs_cont_select
}

const isMobileDevice = () => {
  return (typeof window.orientation !== "undefined") || (navigator.userAgent.indexOf('IEMobile') !== -1);
};

const openSelect = (idx) => {
  const idx1 = idx.getAttribute(DATA_N_SELECT);
  const $ulContLi = document.querySelectorAll(' .cont_select_int > li');

  const selectElementOpen = document.querySelectorAll("[data-indx-select='" + idx1 + "'] select")[0];

  let hg = 0;
  if (isMobileDevice()) {
    if (window.document.createEvent) { // All
      const evt = window.document.createEvent("MouseEvents");
      evt.initMouseEvent("mousedown", false, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);
      selectElementOpen.dispatchEvent(evt);
    } else if (selectElementOpen.fireEvent) { // IE
      selectElementOpen.fireEvent("onmousedown");
    } else {
      selectElementOpen.click();
    }
  } else {
    for (let i = 0; i < $ulContLi.length; i++) {
      hg += $ulContLi[i].offsetHeight;
    }

    const selectOpen = document.querySelectorAll("[data-indx-select='" + idx1 + "']")[0]
      .getAttribute('data-selec-open');

    if (selectOpen === 'false') {
      document.querySelectorAll("[data-indx-select='" + idx1 + "']")[0].setAttribute('data-selec-open', 'true');
      document.querySelectorAll("[data-indx-select='" + idx1 + "'] > .cont_list_select_mate > ul")[0]
        .style.height = "15rem";
    } else {
      document.querySelectorAll("[data-indx-select='" + idx1 + "']")[0].setAttribute('data-selec-open', 'false');
      document.querySelectorAll("[data-indx-select='" + idx1 + "'] > .cont_list_select_mate > ul")[0]
        .style.height = "0px";
    }
  }
} // fin function open_select

const unselect = (index) => {
  document.querySelectorAll("[data-indx-select='" + index + "'] > .cont_list_select_mate > ul")[0].style.height = "0px";
  document.querySelectorAll("[data-indx-select='" + index + "']")[0].setAttribute('data-selec-open', 'false');
}

const _selectOption = (index, select) => {
  if (isMobileDevice()) {
    select = select - 1;
  }
  const select_ = document.querySelectorAll("[data-indx-select='" + select + "'] > select")[0];

  const $liList = document.querySelectorAll("[data-indx-select='" + select + "'] .cont_select_int > li");
  const $pActive = document.querySelectorAll("[data-indx-select='" + select + "'] > .selected-option")[0]
  $pActive.innerHTML = $liList[index].innerHTML;

  for (let i = 0; i < $liList.length; i++) {
    if ($liList[i].className === 'active') {
      $liList[i].className = '';
    }
    $liList[index].className = 'active';
  }

  const selectOptions = document.querySelectorAll("[data-indx-select='" + select + "'] > select > option");
  selectOptions[index].selected = true;
  select_.selectedIndex = index;
  select_.onchange();

  unselect(select);
}
