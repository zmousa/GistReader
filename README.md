Gists Reader
============
Android application to read list of user Gists (GitHub Gists), display them, and let the user make the following actions on his gists:
 * Star a gist.
 * Unstar a gist.
 * Delete a gist.

Features
--------
 * Login using stataic token.
 * User can click on a gist from the list to view it's content.
 * The gists list should be cached on the disk and refreshed on app startup.
 * User can use the app offline, meaning that when there is no internet connection user can do actions on any gist, and the app should cache those actions and replay them when the internet connection is back on.

Dependencies
------------
 * [GreenDao](https://github.com/greenrobot/greenDAO) ORM solution for Android.
 * [Volley] (https://android.googlesource.com/platform/frameworks/volley) Framework for Android network requests.
