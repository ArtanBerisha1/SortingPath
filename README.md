# Sorting Path App
- Idea of the app is to visualize sorting algorithms, similar with some implementations that I saw on web, so first you have to draw the "Path" and then "Sort" it.
- Here is a short preview of how it works :) 
![screen-gif](sortAppPrev.gif)

#### Also I tried to learn and implement some new libraries that were new to me, e.g:
1. [Jetpack Compose](https://developer.android.com/jetpack/compose/setup) -> for UI
2. [SoundPool](https://developer.android.com/reference/android/media/SoundPool) -> is used for audio, in my case a play a sound everytime the main index changes (its very light weight and easy to use)
3. [PlantUML](https://plantuml.com/starting) -> which I am trying to use to draw the diagrams for the algorithms, this is still in progress since I didn't have time to learn it completely. 
I also checked [Mermaid Chart](https://docs.mermaidchart.com/mermaid/intro)
but still I am not sure which one to use, since my main goal is to be able to track the diagram changes with git and also visualise them in markdown file.

#### How to install the app, so you can play/test it
1. First [download a released apk](https://github.com/ArtanBerisha1/SortingPath/releases/tag/0.1)
2. Make sure you have [Android Debug Bridge](https://source.android.com/docs/setup/build/adb) and USB debugging enabled on your phone
3. Then using this command: `adb install "path/to/downloaded/apk_file"`, e.g: `adb install ~/Downloads/sortingPath.apk`


#### List of sorting algorithms to implement/implemented:
- [x] Bubble Sort
- [x] Insertion Sort
- [x] Selection Sort
- [x] Heap Sort (more work is needed)
- [x] Merge Sort (more work is needed)
- [ ] Quick Sort 

#### TODOs: 
- **Bugfix**: ~~UI bug after Process Button is clicked~~
- **Bugfix**: ~~Clicking Process Button while we are sorting removes drawings~~
- **Feature**: ~~Check if the array is sorted before sorting~~
- **Feature**: Add flowcharts for the algorithms [In progress]
- **Bugfix**: (Optional) Implement correctly Heap Sort and Merge Sort [Todo]
- **Feature**: Implement Quick Sort [Todo]
- Publish App on play store [Todo]



## License

[Apache License 2.0](LICENSE)