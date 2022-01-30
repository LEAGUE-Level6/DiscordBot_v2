Please find the unit test template for your feature at:
src/test/java/org/jointheleague/features/templates/FeatureTemplateTest

For your feature, you will want to update line 26 to be an instance of your class, then use the replace all feature
to replace "featureTemplate" with the name of your instance throughout the test class.

Note that the "instanceof FeatureTemplate" on line 61 should remain the same.  It is used to make sure that your class
has a command that is different from the default template command, without causing the FeatureTemplate class to fail
this unit test.