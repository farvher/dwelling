package com.dwelling.app.exceptions

class AzureBlobStorageServiceException(message : String) : Exception(message)

class IsNotImageException(message: String)  : Exception(message)

class SearchServiceException(message: String) : Exception(message)

class PropertyNotFoundException(message : String) : Exception(message)

class ZoneNotFoundException(message : String) : Exception(message)

class CityNotFoundException(message : String) : Exception(message)

class NeighborhoodNotFoundException(message : String) : Exception(message)

class CountryNotFoundException(message : String) : Exception(message)

