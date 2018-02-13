<?php

// autoload_static.php @generated by Composer

namespace Composer\Autoload;

class ComposerStaticInita9410b29a1e8b507a92181ce5fdff554
{
    public static $prefixLengthsPsr4 = array (
        'S' => 
        array (
            'Stripe\\' => 7,
        ),
    );

    public static $prefixDirsPsr4 = array (
        'Stripe\\' => 
        array (
            0 => __DIR__ . '/..' . '/stripe/stripe-php/lib',
        ),
    );

    public static function getInitializer(ClassLoader $loader)
    {
        return \Closure::bind(function () use ($loader) {
            $loader->prefixLengthsPsr4 = ComposerStaticInita9410b29a1e8b507a92181ce5fdff554::$prefixLengthsPsr4;
            $loader->prefixDirsPsr4 = ComposerStaticInita9410b29a1e8b507a92181ce5fdff554::$prefixDirsPsr4;

        }, null, ClassLoader::class);
    }
}
