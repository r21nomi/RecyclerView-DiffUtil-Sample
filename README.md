# RecyclerView-DiffUtil-Sample
Sample app using [DiffUtil](https://developer.android.com/reference/android/support/v7/util/DiffUtil.html) and [Paging Library](https://developer.android.com/topic/libraries/architecture/paging.html).

## Setup(optional)
### Registering your app
This sample app is using GitHub API.

To extend the request limit,
1. [Register your application on GitHub](https://github.com/settings/applications/new).
2. Make `secrets.properties` file under project directory.
3. Fill the `Client ID` and `Client Secret` into `secrets.properties`.
```
GITHUB_CLIENT_ID=your_github_client_id
GITHUB_CLIENT_SECRET=your_github_client_secret
```