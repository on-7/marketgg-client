window.addEventListener('DOMContentLoaded', () => {
  console.log('ready');

  const foo = document.querySelector('#sidebarCollapse');

  foo.addEventListener('click', () => {
    console.log('click');

    const bar = document.querySelector('#sidebar');
    bar.classList.toggle('active');
  })

  const button = document.querySelector('.collapse');

  // Grab all the trigger elements on the page
  const triggers = Array.from(document.querySelectorAll('[data-toggle="collapse"]'));

  // Listen for click events, but only on our triggers
  window.addEventListener('click', (ev) => {
    const elm = ev.target;
    if (triggers.includes(elm)) {
      const selector = elm.getAttribute('data-target');
      collapse(selector, 'toggle');
    }
  }, false);

  const fnmap = {
    'toggle': 'toggle',
    'show': 'add',
    'hide': 'remove'
  };

  const collapse = (selector, cmd) => {
    const targets = Array.from(document.querySelectorAll(selector));
    targets.forEach(target => {
      target.classList[fnmap[cmd]]('show');
    });
  }
});
