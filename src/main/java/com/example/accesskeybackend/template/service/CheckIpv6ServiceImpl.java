package com.example.accesskeybackend.template.service;

import org.springframework.stereotype.Service;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;

@Service
public class CheckIpv6ServiceImpl implements CheckIpv6Service {

    @Override
    public boolean checkIpv6(String siteUrl) throws SocketException {

        URL url = checkUrl(siteUrl);
        if (url != null) {
            return supportsIPv6(NetworkInterface.getByName(String.valueOf(url)));
        }

        return false;
    }

    private URL checkUrl(String siteUrl) {

        URL url;
        try {
            url = new URL(siteUrl);
        } catch (MalformedURLException e) {
            if(!siteUrl.startsWith("http:") && !siteUrl.startsWith("https:")){
                checkUrl(new StringBuilder("http://").append(siteUrl).toString());
            }
            return null;
        }

        return url;
    }

    private static boolean supportsIPv6(NetworkInterface nif) throws SocketException {
        return Collections.list(nif.getNetworkInterfaces()).stream()
                .map(NetworkInterface::getInterfaceAddresses)
                .flatMap(Collection::stream)
                .map(InterfaceAddress::getAddress)
                .anyMatch(((Predicate<InetAddress>) InetAddress::isLoopbackAddress)
                        .negate().and(address -> address instanceof Inet6Address));
    }
}
