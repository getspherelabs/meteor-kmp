module.exports = {
  sidebar: [
    {
      type: 'doc',
      id: 'index',
    },
    {
      type: 'category',
      label: 'Guides',
      collapsed: true,
      items: [
        'guides/getting-started',
        'guides/concepts',
      ],
    },
    {
      type: 'category',
      label: 'Common Components',
      collapsed: true,
      items: [
        'common/vm',
        'common/flow',
        'common/usecase',
      ]
    },
    {
      type: 'category',
      label: 'Tests',
      items: [
        'test/tests',
      ]
    }
  ],
};
