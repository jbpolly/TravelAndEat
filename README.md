# **Travel & Eat**

**This is app was made as a Capstone project for the Udacity Android Kotlin Developer Nanodegree Program.**  

![icon_foreground](https://user-images.githubusercontent.com/7203221/135765441-1730d4e8-64d7-4f96-8825-aa3fb712092b.png)

## Overview

The app is a companion for people who like to travel and try new foods. In the app, it is possible to create entries with places you would like to go and what dishes you would like to taste in that place. It is also possible to add the amount of money it will cost and mark the ones that have already being visited. When you are close to one of the places with a meal saved, the user receives a notification remembering them to enjoy the saved meal.

## Features implemented

### MVVM architecture
MVVM architecture is a Model-View-ViewModel architecture that removes the tight coupling between each component. Most importantly, in this architecture, the children don't have the direct reference to the parent.

Model: It represents the data and the business logic of the Android Application. It consists of the business logic - local and remote data source, model classes, repository.

View: It consists of the UI Code(Activity, Fragment), XML. It sends the user action to the ViewModel but does not get the response back directly.

ViewModel: It is a bridge between the View and Model. It does not have a direct reference to the View. It interacts with the Model and exposes the observable that can be observed by the View.

To achieve this architecture, the app uses Android Jetpack ViewModel to implement the ViewModel layer and Android Jetpack Live Data to allow the View layer to observe the change on the data in the ViewModel layer.

###  Room

The app also uses the Room from Android Jetpack libraries. The Room persistence library provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite.
The meal reminders create on the app are saved on the Room so the data can persist through sessions. The Room is also used to cache some of the meal information retrieved from the network so the user will be able to access some information about the meal categories present in the app even if there is no network connection.

### Network communication with Retrofit

Retrofit is a type-safe HTTP client for Android and Java and it is used on this project to facilitate the communication with the API.
The API used on this project is called [TheMealDB](https://www.themealdb.com/api.php "TheMealDB"). The MealDB is an open, crowd-sourced database of Recipes from around the world. This API is used to browse meal categories and see the food items in each of them and also to search a meal by name to add to the user's meal reminder.

### Dependency injection with Koin
Koin is a smart Kotlin injection library and it is used to make the separation of concerns and decoupling of layers less verbose.

### Navigation
Android Jetpack's Navigation component helps you implement navigation, from simple button clicks to more complex patterns, such as app bars and the navigation drawer. The Navigation component also ensures a consistent and predictable user experience by adhering to an established set of principles.

###  Geofences
When a meal reminder is saved, a geofence is created to notify the user when they are close to the place where they will enjoy their meals. To mark a location of interest, you specify its latitude and longitude. To adjust the proximity for the location, you add a radius. The latitude, longitude, and radius define a geofence, creating a circular area, or fence, around the location of interest.
Geofence is implemented using the Google Geofencing API.

###  Google maps
To save a location and create its geofence, it is necessary to select the position on a map. Then, its latitude, longitude, name, and other informations are used to implement the other features form the app.
To allow the user to select a position on the map the Maps SDK for Android is used. This way, it is possible to use an embedded google map to interact with the map, select locations and retrieve information about it.

### Push notifications
When the user enter a geofence radius, they are notified using a push notification. The notifications are built and triggered locally and when the user interact with it they are taken to inside the app.

### Network image loading
The information about the meals and its categories also provide us to an URL to their thumb image. These URLs are loaded into ImageViews using the library [Glide](https://bumptech.github.io/glide/ "Glide").
Glide is a fast and efficient image loading library for Android focused on smooth scrolling. Glide offers an easy to use API, a performant and extensible resource decoding pipeline and automatic resource pooling.

### Permission request
It is necessary to request the user some permissions to use some features of the app, like their location. Some of these informations are sensitive and require explicit permission from the user. Before using any of the features that requires explicit permission, the user is prompted with a dialog explaining the need of the persmission and then the permission is requested by the system.

### Motion Layout
Motion layout is used to implement some animations on the first screen of the app.

## App screens and flow

### App enter screen
This is the first screen that the user see when entering the app. There is an animation done using motion layout. Clicking the "Enter" button takes the user to inside the app and the enter screen is popped out of the stack.

![app_enter_resize](https://user-images.githubusercontent.com/7203221/135765779-372b890e-1d5d-457f-872e-8103ebb87d09.png)

### Dashboard screen
This is the main screen of the app. From this screen it is possible to:

- Visualize saved meals
If there are no saved items, this list should display a message informing that there are no items to display.
If there are items saved, the user can visualize their saved meals. Clicking on an item takes the user to the "Meal Details" screen. Clicking in the checkbox mark/unmark the item as completed or not.

- Go to "Explore" screen"

- Go to "Add Places" screen

- Go to "Credits" screen
Option accessible through the options menu
- Clear all items on the saved list
Option accessible through the options menu

![app_dash_empty_resize](https://user-images.githubusercontent.com/7203221/135765956-cc5d978f-0ff1-4ede-bcac-c2e35d93038c.png)
![app_dash_filled_resize](https://user-images.githubusercontent.com/7203221/135765959-22c850bd-5dcb-49be-ae2c-5bc8592bdc85.png)
![app_dash_menu_resize](https://user-images.githubusercontent.com/7203221/135765960-77cfa1fd-8f0d-4c56-80f2-1f76954bd9db.png)

### Explore Food screen
If the user selects the option to go to the "Explore Food" screen the user will be directed to a screen where they will be able to see the different meal categories from the API database. Clicking on one of the options take the user to the "Meal from category" screen. If it is not possible to retrieve the information or there are no items the user is notified about it. The categories information is also cached in the room locally so the user should be able to see this information without network connection if they have already accessed this information before.

![app_food_category_resize](https://user-images.githubusercontent.com/7203221/135766186-512389c4-67cb-48f3-855c-5f7d1500bba2.png)

### Meal from category screen
In this screen the user will be able to see a list with all the meals available in a certain category. If it is not possible to retrieve the information or there are no items the user is notified about it.

![app_meals_category_resize](https://user-images.githubusercontent.com/7203221/135766260-caa10cfa-a2ff-48b0-8456-c95c43f1d5ac.png)

### Credits screen
In this screen the user will be able to see a list with credits to some of the image resources used in the app and a disclaimer informaing this app has no commercial purposes.

![app_credits_resize](https://user-images.githubusercontent.com/7203221/135766307-870ae40b-e25f-4d62-910d-4724f0b2a95c.png)

### Add/Edit screen
This is screen can be accessed by clicking in the "Add Place" option on the dashboard screen or by clicking in an item frmo the dashboard meal list to edit its information.
If the user clicks on "Add Place" then the screen does not show an option to delete the item. If the user is editing an existing item there will be an option to delete the item besides the "Save" button.
In this screen the user inputs information about the meal name, the location where they will get this meal and the price of the meal.
When the user clicks on the meal name input field, they are redirect to the "Select Meal" screen.
When the user clicks on the meal location input field, they are redirect to the "Select Location" screen.
When the user cliks on save, the information inserted is validated. If the info is not valid then the user is notified about it. If it is valid then the app will request permissions to create a geofence. At the end of the permission request, independently of the permission being given or not, the information is saved and stored locally. Then, the app goes back to the "Dashboard" screen where the user should be able to see their new inserted item.

![app_add_edit_empty_resize](https://user-images.githubusercontent.com/7203221/135766490-f808771d-5dc9-4b29-b396-73d372106058.png)
![app_edit_resize](https://user-images.githubusercontent.com/7203221/135766505-35345ffd-5e78-4fc4-96c8-5c4a42c59b25.png)

### Select meal screen
In this screen the user will be able to search for a meal by name. There is a debounce on the search text input field to avoid too many calls to the API.
When the user clicks on one item from the list then the meal is selected and the user is redirected back to the "Add/Edit" screen with the information updated according to the meal selected. If no items can retrieved or the list is empty then the user is notified accordingly.

![app_search_meal_resize](https://user-images.githubusercontent.com/7203221/135766643-f85c254f-cb47-40df-8601-607ab63b5e5b.png)

### Select location screen
In this screen the user will be able to search for a location. A location on the map is selected when the user clicks on a POI in the map or long click in any place of the map.
Then, a dialog appears to let they confirm the location is correct. On confirmation, the user is redirected back to the "Add/Edit" screen with the information updated according to the location selected.

![app_select_location_resize](https://user-images.githubusercontent.com/7203221/135766661-1236a3e5-ce8e-452e-8211-e4f3a1296caf.png)
![app_select_loc_confirm_resize](https://user-images.githubusercontent.com/7203221/135766703-035a17ea-3795-4b26-89c6-2b7b70b684cc.png)


### Meal details screen
In this screen the user will be able to see information about their saved meal. This is screen is accessed by cliking on the notification that the user receives when entering a geofence or clicking on a saved item from the dashboard.
If the user arrived in this screen from a saved item on the dashboard then the user can see a button to edit the meal info. If the user arrives from the notification this button is hidden.

![app_notification](https://user-images.githubusercontent.com/7203221/135767325-545c9405-9f66-452d-be52-66aa444fb214.png)
![app_meal_details_resize](https://user-images.githubusercontent.com/7203221/135767309-b718ab02-4ab6-4f9a-8a0c-5e838e56ce6c.png)
![app_meal_details_editable_resize](https://user-images.githubusercontent.com/7203221/135767311-652c2a9c-e911-4db8-a818-f9fa8683700c.png)

## Before executing
Before trying to build the app remember that the user shoudl provide a Google Maps API key in the file google_maps_api.xml

