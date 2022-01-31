package com.example.samplefoodapp.commonutils

enum class FoodCategories(val value: String) {

    CHICKEN("Chicken"),
    BEEF("Beef"),
    SOUP("Soup"),
    DESSERT("Dessert"),
    VEGETARIAN("Vegetarian"),
    MILK("Milk"),
    VEGAN("Vegan"),
    PIZZA("Pizza"),
    DONUT("Donut"),
}

fun getAllFoodCategories(): ArrayList<FoodCategories>{
    return arrayListOf(FoodCategories.CHICKEN,
        FoodCategories.BEEF, FoodCategories.SOUP,
        FoodCategories.DESSERT, FoodCategories.VEGETARIAN, FoodCategories.MILK,
        FoodCategories.VEGAN, FoodCategories.PIZZA, FoodCategories.DONUT
    )
}

fun getFoodCategory(value: String): FoodCategories? {
    val map = FoodCategories.values().associateBy(FoodCategories::value)
    return map[value]
}
fun getFoodcategoriesIndex(value:String):Int{
    for(i in getAllFoodCategories().indices){
        if(getAllFoodCategories().get(i).value.equals(value)){
           return i;
        }
    }
    return -1;
}
