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
        {
          type: 'category',
          label: 'Working with Rooms',
          collapsed: true,
          items: [
            'guides/room/connect',
            'guides/room/publish',
            'guides/room/receive',
            'guides/room/data',
          ],
        },
        'guides/access-tokens',
        'guides/server-api',
        'guides/webhooks',
        // 'guides/recording',
      ],
    },
    {
      type: 'category',
      label: 'Deployment',
      collapsed: true,
      items: [
        'deploy',
        'deploy/vm',
        'deploy/kubernetes',
        'deploy/ports-firewall',
        'deploy/distributed',
        'deploy/test-monitor',
        'deploy/benchmark',
      ]
    },
    {
      type: 'category',
      label: 'API Reference',
      collapsed: false,
      items: [
       'references/client-sdks',
       'references/server-sdks',
      ]
    },
    {
      type: 'category',
      label: 'Internals',
      items: [
        'internals/client-protocol',
      ]
    }
  ],
};
